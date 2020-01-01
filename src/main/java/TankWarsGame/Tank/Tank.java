package TankWarsGame.Tank;

// TODO --> not sure if this calss is ever used, maybe when tanks have different sizes??
public class Tank {
     private int length =0;
     private int horizontalPosition;
     private int verticalPosition;


     /*********************************
      * Constructors
      **/

     // default constructor with default tank size
     public Tank(int horizontalPosition, int verticalPosition){
         this.length = 1;
         this.horizontalPosition = horizontalPosition;
         this.verticalPosition = verticalPosition;
     }


     /*********************************
      * getter and setter methods
      **/

     public int getHorizontalPosition() {
         return horizontalPosition;
     }

     public int getVerticalPosition() {
         return verticalPosition;
     }

     public void setHorizontalPosition(int horizontalPosition) {
         this.horizontalPosition = horizontalPosition;
     }

     public void setVerticalPosition(int verticalPosition) {
            this.verticalPosition = verticalPosition;
        }

    }
