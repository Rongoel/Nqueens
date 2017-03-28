package nqueens2;

import processing.core.PApplet;


public class NQueens2 extends PApplet {
    static int[][] board = null;

    private static void displayBoard( int N ) {
            char escCode = 0x1B;
            
            System.out.println(String.format("%c[%d;%df", escCode, 0, 0 ));
            
            for ( int c=0; c < N; c++ )
                    System.out.print( "--" );
            System.out.println();
                    
            
            for ( int r=0; r < N; r++ ) {
                    for ( int c=0; c < N; c++ ) {
                            if ( board[r][c] == -1 )
                                    System.out.print( "Q " );
                            else if ( board[r][c] == 0 )
                                    System.out.print( ". " );
                            else 
                                    System.out.print( "* " );
                    }
                    System.out.println();
            }
            
            try {
                    Thread.sleep( 2000 );
            } catch (InterruptedException e) {
                    
            }
    }

    private static void displayBoardN(int N) {
            
            System.out.println(String.format("%c[%d;%df", 0x1B, 0, 0 ));
            
            for ( int c=0; c < N; c++ )
                    System.out.print( "--" );
            System.out.println();
                    
            for ( int r=0; r < N; r++ ) {
                    for ( int c=0; c < N; c++ ) 
                            System.out.print( String.format( "%2d", board[r][c]) );
                    System.out.println();
            }
    }

   /* Places N numbers of queens on the NxN board
    * This occurs until all queens are placed on the board.
    * Return true if all have been successfully been placed
    */
    private static boolean recursePlaceQueen( int row, int N ) {
            
        // We stardet on row 1. Try all the columns that have a open space to place a queen
            for ( int col = 0; col < N; col++ ) {
                    
                    // is this spot taken?
                    if ( board[row][col] == 0 ) {
                            
                            //no; Lets put a queen here, for now
                            board[row][col] = -1; 
                            
                            // Is this the last row? if yes this is done 
                            if ( row == N-1 )
                                    return true;

                          // If not mark all the cells down from this row that, the queen we just have placed, has taken  
                            updateColumnDown( row, col, N, +1 );
                            updateDiagonals( row, col, N, +1 );
                            
                            // show the current board
                            displayBoard( N );
                            
                            
                            boolean success = recursePlaceQueen( row+1, N );
                            
                            //have we placed all Queens in the correct way?
                            if ( success ) 
                                   // if true. The function is done
                                    return true;

                            //if the function was unable to place all queens with the queen in its current place, remove it
                            board[row][col] = 0;

                            //undo any invalidation done earlier
                            updateColumnDown( row, col, N, -1 );
                            updateDiagonals( row, col, N, -1 );
                            
                            
                            displayBoard( N );
                            // continue with looking for a new postion for the queens on Row row
                            
                    }
            }
       
            return false;
    }

  /*given the queens current position mark all rows below and on diagonal positions as taken
   * 
   */
    private static void updateDiagonals(int row, int col, int N, int offset) {
            int leftC = col-1, rightC = col+1;
            for ( int r=row+1; r<N; r++ ) {
                    if ( leftC >= 0 ) board[r][leftC]  += offset;
                    leftC--;
                    if ( rightC < N ) board[r][rightC] += offset;
                    rightC++;
            }
    }

    /*Given the queens position mark all cells on rows just below the queens cell as taken, if it is offset by 1 mark it as not taken
     * 
     */
    private static void updateColumnDown(int row, int col, int N, int offset ) {
            for ( int r = row+1; r < N; r++ )
                    board[ r ][ col ] += offset;
    }

    // create a board with the dimensions NxN with empty cells in it
    private static int[][] createBoard( int N ) {
            board = new int[N][];
            
            for ( int i=0; i<N; i++ ) 
                    board[i] = new int[N];
            
            for ( int i=0; i<N; i++ )
                    for ( int j=0; j<N; j++ )
                            board[i][j] = 0;
            
            return board;
    }

    //main entry point, Change content of args, to change how big the board is and how many queens there are
    public static void main(String[] args) {
            
            if ( args.length == 0 ) {
                    args = new String[] { "6" };
            }
            
            int N = Integer.parseInt( args[0] );
            board = createBoard( N );
            
            recursePlaceQueen(0, N );
            displayBoard( N );
    }
}
