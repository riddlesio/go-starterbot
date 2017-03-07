// // Copyright 2016 theaigames.com (developers@theaigames.com)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//  
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

package bot;

import java.util.ArrayList;
import java.util.HashMap;

import field.Field;
import move.Move;
import player.Player;

/**
 * Field class
 * 
 * Handles everything that has to do with the field, such 
 * as storing the current state and performing calculations
 * on the field.
 * 
 * @author Jim van Eeden <jim@starapple.nl>, Joost de Meij <joost@starapple.nl>
 */

public class BotState {
    private int MAX_TIMEBANK;
    private int TIME_PER_MOVE;
    private int MAX_ROUNDS;
    
    private int roundNr;
    private int moveNr;
    private int timebank;
    private String myName;
    private HashMap<String, Player> players;
    
    private Field field;
    
    public BotState() {
        this.field = new Field();
        this.players = new HashMap<String, Player>();
    }
    
    /**
     * Parses all the game settings given by the engine
     * @param key : type of data given
     * @param value : value
     */
    public void parseSettings(String key, String value) {
        try {
            switch(key) {
            case "timebank":
                int time = Integer.parseInt(value);
                this.MAX_TIMEBANK = time;
                this.timebank = time;
                break;
            case "time_per_move":
                this.TIME_PER_MOVE = Integer.parseInt(value);
                break;
            case "player_names":
                String[] playerNames = value.split(",");
                for(int i=0; i<playerNames.length; i++)
                    players.put(playerNames[i], new Player(playerNames[i]));
                break;
            case "your_bot":
                this.myName = value; 
                break;
            case "your_botid":
                int myId = Integer.parseInt(value);
                int opponentId = 2 - myId + 1;
                this.field.setMyId(myId);
                this.field.setOpponentId(opponentId);
                break;
            case "field_width":
                this.field.setColumns(Integer.parseInt(value));
                break;
            case "field_height":
                this.field.setRows(Integer.parseInt(value));
                break;
            case "max_rounds":
            	this.MAX_ROUNDS = Integer.parseInt(value);
            	break;
            default:
                System.err.println(String.format("Cannot parse settings input with key '%s'", key));
            }
       } catch (Exception e) {
           System.err.println(String.format("Cannot parse settings value '%s' for key '%s'", value, key));
           e.printStackTrace();
       }
    }
    
    /**
     * Parse data about the game given by the engine
     * @param key : type of data given
     * @param value : value
     */
    public void parseGameData(String key, String value) {
        try {
            switch(key) {
            case "round":
                this.roundNr = Integer.parseInt(value);
                break;
            case "move":
                this.moveNr = Integer.parseInt(value);
                break;
            case "field":
                this.field.initField();
                this.field.parseFromString(value);
                break;
            default:
                System.err.println(String.format("Cannot parse game data input with key '%s'", key));
            }
        } catch (Exception e) {
            System.err.println(String.format("Cannot parse game data value '%s' for key '%s'", value, key));
            e.printStackTrace();
        }
    }
    
    /**
     * Parse data about given player that the engine has sent
     * @param playerName : player the data is about
     * @param key : type of data given
     * @param value : value
     */
    public void parsePlayerData(String playerName, String key, String value) {
    	System.err.println(playerName + " " + key + " " + value);
        try {
            switch(key) {
            case "points":
                this.players.get(playerName).setPoints(Double.parseDouble(value));
                break;
            case "last_move":
            	this.players.get(playerName).setLastMove(value);
            	break;
            default:
                System.err.println(String.format("Cannot parse %s data input with key '%s'", playerName, key));
            }
        } catch (Exception e) {
            System.err.println(String.format("Cannot parse %s data value '%s' for key '%s'", playerName, value, key));
            e.printStackTrace();
        }
    }

    public ArrayList<Move> getAvailableMoves() {
        return field.getAvailableMoves();
    }
    
    public void setTimebank(int value) {
        this.timebank = value;
    }
    
    public int getTimebank() {
        return this.timebank;
    }
}