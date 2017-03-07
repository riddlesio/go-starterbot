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

package field;

import java.util.ArrayList;

import move.Move;

/**
 * Field class
 * 
 * Handles everything that has to do with the field, such 
 * as storing the current state and performing calculations
 * on the field.
 * 
 * @author Jim van Eeden <jim@starapple.nl>, Joost de Meij <joost@starapple.nl>
 */

public class Field {
    private int myId;
    private int opponentId;
    private int rows;
    private int cols;
    
	private String[][] field;
	private final String EMPTY_FIELD = ".";
	
	public Field() {}
	
	/**
	 * Initializes field
	 * @throws Exception
	 */
	public void initField() throws Exception {
	    try {
	        this.field = new String[this.cols][this.rows];
	    } catch (Exception e) {
	        throw new Exception("Error: trying to initialize field while field " 
	                + "settings have not been parsed yet.");
	    }
	    clearField();
	}

	/**
	 * Parse field from comma separated String
	 * @param String : input from engine
	 */
	public void parseFromString(String s) {
		s = s.replace(";", ",");
		String[] r = s.split(",");
		int counter = 0;
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				field[x][y] = r[counter]; 
				counter++;
			}
		}
	}
	
	/**
	 * Sets the whole field to empty
	 */
	public void clearField() {
	    for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                field[x][y] = EMPTY_FIELD;
            }
        }
	}

	/**
	 * Returns a list of all available moves. i.e. empty cells 
	 * that will not result in a suicide move or violate the Ko rule
	 * @return : a list of all available moves in this game state
	 */
	public ArrayList<Move> getAvailableMoves() {
	    ArrayList<Move> moves = new ArrayList<Move>();
	    
	    for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (isEmptyPoint(x,y)) {
                    moves.add(new Move(x, y));
                }
            }
        }
	    
		return moves;
	}
	
	public boolean isEmptyPoint(int x, int y) {
	    return field[x][y].equals(EMPTY_FIELD);
	}
	

		
	@Override
	/**
	 * Creates comma separated String that represents the field
	 * @param args : 
	 * @return : Comma separated string of player Id's or 0 for empty.
	 */
	public String toString() {
		String s = "";
		int counter = 0;
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				if (counter > 0) {
					s += ",";
				}
				s += field[x][y];
				counter++;
			}
		}
		return s;
	}
	
	public void setColumns(int value) {
        this.cols = value;
    }
    
    public int getColumns() {
        return this.cols;
    }
    
    public void setRows(int value) {
        this.rows = value;
    }
    
    public int getRows() {
        return this.rows;
    }
    
    public void setMyId(int id) {
        this.myId = id;
    }
    
    public void setOpponentId(int id) {
        this.opponentId = id;
    }
}