import javax.swing.ImageIcon;

public class ImageLoader {
    // METODO EXTRACT CLASS de la clases King, Queen, Bishop, Knight, Rook, Pawn
    // esta clase se encarga de cargar las imagenes de las piezas
    // se elimina el codigo duplicado de las clases King, Queen, Bishop, Knight, Rook, Pawn
    // author: Kevin Ramos Rivas
    String type;
    int color;
    public ImageLoader(String type, int color){
        this.type = type;
        this.color = color;
    }

    public ImageIcon createImageByPieceType(){
        if ( color == 0 ){
            return new ImageIcon(
                getClass().getResource( "chessImages/" +"Black"+ type + ".gif" ) );
        }
        else if ( color == 1 ){
            return new ImageIcon(
                getClass().getResource( "chessImages/" +"White"+ type + ".gif" ) );
        }
        else
        {
            return new ImageIcon(
                getClass().getResource( "chessImages/default-Unassigned.gif" ) );
        }
    }


}
