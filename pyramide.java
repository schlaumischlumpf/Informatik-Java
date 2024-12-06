import javakara.JavaKaraProgram;
        
/* BEFEHLE:  kara.
 *   move()  turnRight()  turnLeft()
 *   putLeaf()  removeLeaf()
 *
 * SENSOREN: kara.
 *   treeFront()  treeLeft()  treeRight()
 *   mushroomFront()  onLeaf()
 */
public class pyramide extends JavaKaraProgram {
  int y = 0;
  boolean weg_hin = false;
  public void zahlen_eingabe() {
    while (y <= 0) {
      this.y = tools.intInput("Geben Sie bitte die Höhe der Pyramide ein. Die Zahl muss größer als 0 sein. ");
      if (y <= 0) {
        tools.showMessage("Die Zahl war nicht größer als 0. Probieren Sie es bitte erneut.");
      }
    }
  }

  public void baue_pyramide() {
    if (weg_hin == false) {
      for (int i = 0; i < y*2-1; i++) {
        if (!kara.onLeaf()) {
          kara.putLeaf();
        }
        if (!kara.treeFront()){
          kara.move();
        }
      }
      y = y - 1;
      if (y > 0)  {
        kara.turnRight();
        kara.turnRight();
        kara.move();
        kara.move();
        kara.turnRight();
        kara.move();
        kara.turnLeft();
      }
      weg_hin = true;
    } else {
      if (weg_hin == true) {
        for (int i = 0; i < y*2-1; i++) {
        if (!kara.onLeaf()) {
          kara.putLeaf();
        }
          if (!kara.treeFront()){
            kara.move();
          }
        }
        y = y - 1;
        if (y > 0)  {
        kara.turnRight();
        kara.turnRight();
        kara.move();
        kara.move();
        kara.turnLeft();
        kara.move();
        kara.turnRight();
        }
        weg_hin = false;
      }
    }
  }

  public void myProgram() {
    world.clearAll();
    int size_x = world.getSizeX();
    int size_y = world.getSizeY();
    this.zahlen_eingabe();
    if (y > size_y) {
      world.setSize (y*2-1, y);
      size_x = world.getSizeX();
    }
    if (y+2-1 > size_x) {
      world.setSize (y*2-1, y);
      size_y = world.getSizeY();
    }

    kara.setPosition (0, y-1);
    while (y > 0) {
      this.baue_pyramide();
    }
  }
}

        