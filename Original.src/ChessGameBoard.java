import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.GridLayout;
// -------------------------------------------------------------------------
/**
 * The panel that represents the Chess game board. Contains a few methods that
 * allow other classes to access the physical board.
 *
 * @author Ben Katz (bakatz)
 * @author Myles David II (davidmm2)
 * @author Danielle Bushrow (dbushrow)
 * @version 2010.11.17
 */
public class ChessGameBoard extends JPanel implements MouseListener { // Se implementa directamente el MouseListener , Autor: Edgar Zenobio
    private BoardSquare[][] chessCells;

    // ----------------------------------------------------------
    /**
     * Returns the entire board.
     *
     * @return BoardSquare[][] the chess board
     */
    public BoardSquare[][] getCells() {
        return chessCells;
    }

    /**
     * Checks to make sure row and column are valid indices.
     * @param row the row to check
     * @param col the column to check
     * @return boolean true if they are valid, false otherwise
     */
    private boolean validateCoordinates(int row, int col) {
        return chessCells.length > 0 && chessCells[0].length > 0 &&
                row < chessCells.length && col < chessCells[0].length
                && row >= 0 && col >= 0;
    }

    // ----------------------------------------------------------
    /**
     * Gets the BoardSquare at row 'row' and column 'col'.
     * @param row the row to look at
     * @param col the column to look at
     * @return BoardSquare the square found, or null if it does not exist
     */
    public BoardSquare getCell(int row, int col) {
        if (validateCoordinates(row, col)) {
            return chessCells[row][col];
        }
        return null;
    }

    // ----------------------------------------------------------
    /**
     * Clears the cell at 'row', 'col'.
     * @param row the row to look at
     * @param col the column to look at
     */
    public void clearCell(int row, int col) {
        if (validateCoordinates(row, col)) {
            chessCells[row][col].clearSquare();
        } else {
            throw new IllegalStateException("Row " + row + " and column" +
                    " " + col + " are invalid, or the board has not been" +
                    "initialized. This square cannot be cleared.");
        }
    }

    // ----------------------------------------------------------
    // create a new method that returns all pieces of a certain color
    public ArrayList<ChessGamePiece> getAllPiecesOfColor(int color) {
        ArrayList<ChessGamePiece> pieces = new ArrayList<ChessGamePiece>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessCells[i][j].getPieceOnSquare() != null
                        && chessCells[i][j].getPieceOnSquare().getColorOfPiece() == color) {
                    pieces.add(chessCells[i][j].getPieceOnSquare());
                }
            }
        }
        return pieces;
    }

    // ----------------------------------------------------------
    /**
     * Create a new ChessGameBoard object.
     */
    public ChessGameBoard() {
        this.setLayout(new GridLayout(8, 8, 1, 1));
        addMouseListener(this); //Se añade el MouseListener en el constructor , Autor: Edgar Zenobio
        chessCells = new BoardSquare[8][8];
        initializeBoard();
    }

    // ----------------------------------------------------------
    /**
     * Clears the board of all items, including any pieces left over in the
     * graveyard, and all old game logs.
     * @param addAfterReset if true, the board will add the BoardSquares
     * back to the board, if false it will simply reset everything and leave
     * the board blank.
     */
    public void resetBoard(boolean addAfterReset) {
        chessCells = new BoardSquare[8][8];
        this.removeAll();
        if (getParent() instanceof ChessPanel) {
            ((ChessPanel) getParent()).getGraveyard(1).clearGraveyard();
            ((ChessPanel) getParent()).getGraveyard(2).clearGraveyard();
            ((ChessPanel) getParent()).getGameLog().clearLog();
        }
        for (int i = 0; i < chessCells.length; i++) {
            for (int j = 0; j < chessCells[0].length; j++) {
                chessCells[i][j] = new BoardSquare(i, j, null);
                if ((i + j) % 2 == 0) {
                    chessCells[i][j].setBackground(Color.WHITE);
                } else {
                    chessCells[i][j].setBackground(Color.BLACK);
                }
                if (addAfterReset) {
                    chessCells[i][j].addMouseListener(this); // Correcion del listenes a this , Autor: Edgar Zenobio
                    this.add(chessCells[i][j]);
                }
            }
        }
        repaint();
        //revalidate();
        // only the combination of these two calls work...*shrug*
    }

    /**
     * (Re)initializes this ChessGameBoard to its default layout with all 32
     * pieces added.
     */
    /*public void initializeBoard() {
        resetBoard(false);
        for (int i = 0; i < chessCells.length; i++) {
            for (int j = 0; j < chessCells[0].length; j++) {
                ChessGamePiece pieceToAdd;
                if (i == 1) // black pawns
                {
                    pieceToAdd = new Pawn(this, i, j, ChessGamePiece.BLACK);
                } else if (i == 6) // white pawns
                {
                    pieceToAdd = new Pawn(this, i, j, ChessGamePiece.WHITE);
                } else if (i == 0 || i == 7) // main rows
                {
                    int colNum = i == 0 ? ChessGamePiece.BLACK : ChessGamePiece.WHITE;
                    if (j == 0 || j == 7) {
                        pieceToAdd = new Rook(this, i, j, colNum);
                    } else if (j == 1 || j == 6) {
                        pieceToAdd = new Knight(this, i, j, colNum);
                    } else if (j == 2 || j == 5) {
                        pieceToAdd = new Bishop(this, i, j, colNum);
                    } else if (j == 3) {
                        pieceToAdd = new King(this, i, j, colNum);
                    } else {
                        pieceToAdd = new Queen(this, i, j, colNum);
                    }
                } else {
                    pieceToAdd = null;
                }
                chessCells[i][j] = new BoardSquare(i, j, pieceToAdd);
                if ((i + j) % 2 == 0) {
                    chessCells[i][j].setBackground(Color.WHITE);
                } else {
                    chessCells[i][j].setBackground(Color.BLACK);
                }
                chessCells[i][j].addMouseListener(this); // Correcion del listenes a this , Autor: Edgar Zenobio
                this.add(chessCells[i][j]);
            }
        }
    } */

    //Correcion de un code smell de tipo long method 
    //autor: Miguel Otoya
    public void initializeBoard() {
        resetBoard(false);  // Restablece el tablero a su estado inicial
        initializePieces(); // Inicializa las piezas del ajedrez
        initializeBoardSquares(); // Inicializa las casillas del tablero
    }
    
    // Inicializa las piezas del ajedrez, incluyendo los peones y las filas principales
    private void initializePieces() {
        initializePawns(); // Inicializa los peones en las filas 1 y 6
        initializeMainRows(); // Inicializa las filas principales (0 y 7) con las piezas restantes
    }
    
    // Inicializa los peones en las filas 1 y 6
    private void initializePawns() {
        for (int j = 0; j < chessCells[0].length; j++) {
            // Crea y agrega un nuevo peón negro en la posición (1, j)
            chessCells[1][j] = new BoardSquare(1, j, new Pawn(this, 1, j, ChessGamePiece.BLACK));
            // Crea y agrega un nuevo peón blanco en la posición (6, j)
            chessCells[6][j] = new BoardSquare(6, j, new Pawn(this, 6, j, ChessGamePiece.WHITE));
        }
    }
    
    // Inicializa las filas principales (0 y 7) con las piezas restantes
    private void initializeMainRows() {
        int[] mainRows = { 0, 7 }; // Índices de las filas principales
        int[] mainPieces = { ChessGamePiece.BLACK, ChessGamePiece.WHITE }; // Colores de las piezas
    
        for (int i : mainRows) {
            int colNum = mainPieces[i == 0 ? 0 : 1]; // Determina el color de las piezas en función de la fila
            // Crea y agrega las piezas restantes en las posiciones correspondientes
            chessCells[i][0] = new BoardSquare(i, 0, new Rook(this, i, 0, colNum));
            chessCells[i][7] = new BoardSquare(i, 7, new Rook(this, i, 7, colNum));
            chessCells[i][1] = new BoardSquare(i, 1, new Knight(this, i, 1, colNum));
            chessCells[i][6] = new BoardSquare(i, 6, new Knight(this, i, 6, colNum));
            chessCells[i][2] = new BoardSquare(i, 2, new Bishop(this, i, 2, colNum));
            chessCells[i][5] = new BoardSquare(i, 5, new Bishop(this, i, 5, colNum));
            chessCells[i][3] = new BoardSquare(i, 3, new King(this, i, 3, colNum));
            chessCells[i][4] = new BoardSquare(i, 4, new Queen(this, i, 4, colNum));
        }
    }
    
    // Inicializa las casillas del tablero, asignando un objeto BoardSquare a cada posición
    private void initializeBoardSquares() {
        for (int i = 0; i < chessCells.length; i++) {
            for (int j = 0; j < chessCells[0].length; j++) {
                if (chessCells[i][j] == null) {
                    // Crea y agrega una casilla vacía en la posición (i, j)
                    chessCells[i][j] = new BoardSquare(i, j, null);
                }
    
                // Configura el color de fondo de la casilla en función de su posición
                if ((i + j) % 2 == 0) {
                    chessCells[i][j].setBackground(Color.WHITE);
                } else {
                    chessCells[i][j].setBackground(Color.BLACK);
                }
    
                chessCells[i][j].addMouseListener(this); // Agrega un MouseListener a la casilla
                this.add(chessCells[i][j]); // Agrega la casilla al contenedor principal (tablero)
            }
        }
    }
    
    

    // ----------------------------------------------------------
    /**
     * Clears the colors on the board.
     */
    public void clearColorsOnBoard() {
        for (int i = 0; i < chessCells.length; i++) {
            for (int j = 0; j < chessCells[0].length; j++) {
                if ((i + j) % 2 == 0) {
                    chessCells[i][j].setBackground(Color.WHITE);
                } else {
                    chessCells[i][j].setBackground(Color.BLACK);
                }
            }
        }
    }

    //-------------------------------------------------
    // Tecnica Move Method 
    // metodo movido de ChessGameEngine a ChessGameBoard ya que es mas
    // apropiado que este metodo este en el tablero, ya que es el tablero
    // el que se encarga de mover las piezas, no el motor del juego.
    // Author: Kevin Ramos Rivas
    //-------------------------------------------------
    public boolean playerHasLegalMoves(int player) {
        ArrayList<ChessGamePiece> pieces = player == ChessGamePiece.WHITE ? getAllPiecesOfColor(ChessGamePiece.WHITE)
                : getAllPiecesOfColor(ChessGamePiece.BLACK);
        for (ChessGamePiece piece : pieces) {
            if (piece.hasLegalMoves(this)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Do an action when the left mouse button is clicked.
     *
     * @param e
     *          the event from the listener
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 &&
                getParent() instanceof ChessPanel) {
            ((ChessPanel) getParent()).getGameEngine()
                    .determineActionFromSquareClick(e);
        }
    }

    /**
     * Unused method.
     *
     * @param e
     *          the mouse event from the listener
     */
    public void mouseEntered(MouseEvent e) { /* not used */
    }

    /**
     * Unused method.
     *
     * @param e
     *          the mouse event from the listener
     */
    public void mouseExited(MouseEvent e) { /* not used */
    }

    /**
     * Unused method.
     *
     * @param e
     *          the mouse event from the listener
     */
    public void mousePressed(MouseEvent e) { /* not used */
    }

    /**
     * Unused method.
     *
     * @param e
     *          the mouse event from the listener
     */
    public void mouseReleased(MouseEvent e) { /* not used */
    }

    /*
     * 
       Se contempla como una clase que realmente no
       hace uso de los metodos contemplados por lo
       que es mejor eliminarla y aplicar el metodo de
       "Extract Method para extraer el unico metodo del
       que hace uso esta clase" y "Extract class" ya que
       los metodos pertenecen a una inferface
    
       Autor: Edgar Zenobio
      
     * Listens for clicks on BoardSquares.
     *
     * @author Ben Katz (bakatz)
     * 
     * @author Danielle Bushrow (dbushrow)
     * 
     * @author Myles David (davidmm2)
     * 
     * @version 2010.11.16
         
    private class BoardListener
        implements MouseListener
    {
        
         * Do an action when the left mouse button is clicked.
         *
         * @param e
         *            the event from the listener
         
        public void mouseClicked( MouseEvent e ){
            if ( e.getButton() == MouseEvent.BUTTON1 &&
                getParent() instanceof ChessPanel ){
                ( (ChessPanel)getParent() ).getGameEngine()
                    .determineActionFromSquareClick( e );
            }
        }
        /**
         * Unused method.
         *
         * @param e
         *            the mouse event from the listener
        
        public void mouseEntered( MouseEvent e ){ /* not used 
        }
        /**
         * Unused method.
         *
         * @param e
         *            the mouse event from the listener
        
        public void mouseExited( MouseEvent e ){ /* not used 
        }
        /**
         * Unused method.
         *
         * @param e
         *            the mouse event from the listener
         
        public void mousePressed( MouseEvent e ){ /* not used 
        }
        /**
         * Unused method.
         *
         * @param e
         *            the mouse event from the listener
         
        public void mouseReleased( MouseEvent e ){ /* not used 
        }
        
    } 
    */
}
