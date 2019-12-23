package TankWarsGame.Tank;

public class Tank {
    private int length =0;


    /*********************************
     * Constructors
     **/

    // constructor to create a ship
    public Tank(int length){
        this.length = length;
    }


    /*********************************
     * getter and setter methods
     **/
    public int getLength() {
        return length;
    }

    public void setLegnth(int length) {
    this.length = length;
    }
}
