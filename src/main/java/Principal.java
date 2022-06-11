import javax.swing.*;
import java.io.IOException;

public class Principal {
    public static void main(String[] args) throws IOException {
        int opcionMenu = -1;
        String[] botones = {"1. Ver gatos ", "2.Salir"};

        do{
            // menu principal
           String opcion = (String) JOptionPane.showInputDialog(null,"Gatitos java","Menu principal", JOptionPane.INFORMATION_MESSAGE,
                   null,botones,botones[0]);

           //Validamos que opcion selecciona el usuario
           for(int i=0;i< botones.length;i++){
               if(opcion.equals(botones[i])){
                   opcionMenu = i;
               }
           }

           switch (opcionMenu){
               case 0:
                   GatosServices.verGatos();
                   break;
               default:
                   break;
           }

        } while(opcionMenu!=1);


    }
}
