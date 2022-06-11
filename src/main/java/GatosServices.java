
import com.google.gson.Gson;
import com.squareup.okhttp.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class GatosServices {

    public static void verGatos() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request =  new Request.Builder().url("https://api.thecatapi.com/v1/images/search").get().build();
        Response response = client.newCall(request).execute();

        String JSON = response.body().string();

        // Quitar los corchetes del string
        JSON = JSON.substring(1,JSON.length());
        JSON =JSON.substring(0,JSON.length()-1);

        // crear Objetos de la cls GSON es decir le enviamos el json para que la clas epueda manipularlos como campos
        Gson gson = new Gson();
        Gatos gatos  = gson.fromJson(JSON,Gatos.class);

        // Redimensionar la imagen
        Image image = null;
        try {
            URL url = new URL(gatos.getUrl());
            image = ImageIO.read(url);

            ImageIcon fondoGato =  new ImageIcon(image);

            if(fondoGato.getIconWidth()>800){
                // redimensionamos la imagen
                Image fondo = fondoGato.getImage();
                Image modificada =  fondo.getScaledInstance(800,600, Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(modificada);
            }

            String menu = "Opciones \n"
                    + "1. Ver Otra imagen \n"
                    + "2. Favoritos \n"
                    + "3.Volver " +
                    " \n";

            String [] botones = {"Ver otra imagen","Favorito", "Volver"};
            String id_gato = gatos.getId();
            String opciones = (String) JOptionPane.showInputDialog(null,
                    menu,
                    id_gato,
                    JOptionPane.INFORMATION_MESSAGE,
                    fondoGato,
                    botones,
                    botones[0]);


            int selecion = -1;
            for(int i=0;i< botones.length;i++){
                if(opciones.equals(botones[i])){
                    selecion = i;
                }
            }

            switch (selecion){
                case 0:
                    verGatos();
                    break;
                case 1:
                    favoritoGato(gatos);
                    break;
                default:
                    break;
            }

        }catch (IOException e){
            System.out.println("Ocurio un problema");
            System.out.println(e);
        }
    }

    public static void favoritoGato(Gatos gatos){
        try{
            OkHttpClient client  = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n\t\"image_id\":\""+gatos.getId()+"\"\n}");
            Request request =  new Request.Builder()
            .url("https://api.thecatapi.com/v1/favourites")
            .post(body)
            .addHeader("Content-Type","application/json")
            .addHeader("x-api-key", gatos.getApiKey())
            .build();

            Response response = client.newCall(request).execute();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public static void verFavorito(String apiKey){
        try {
            OkHttpClient client =  new OkHttpClient();
            Request request =  new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .get()
                    .addHeader("Content-Type","application/json")
                    .addHeader("x-api-key", apiKey)
                    .build();
            Response response = client.newCall(request).execute();

            // Guardamos el string con la respuesta
            String json = response.body().string();

            // creamos el objeto gson
            Gson gson = new Gson();
            GatosFavoritos[] gatosArray = gson.fromJson(json,GatosFavoritos[].class);

            if(gatosArray.length >0){
                int min =  1;
                int max  = gatosArray.length;
                int aleatorio =  (int) (Math.random() * ((max-min)+1)) + min;
                int indice = aleatorio -1;

                GatosFavoritos gatosFav = gatosArray[indice];

                Image image = null;
                try {
                    URL url = new URL(gatosFav.image.getUrl());
                    image = ImageIO.read(url);

                    ImageIcon fondoGato =  new ImageIcon(image);

                    if(fondoGato.getIconWidth()>800){
                        // redimensionamos la imagen
                        Image fondo = fondoGato.getImage();
                        Image modificada =  fondo.getScaledInstance(800,600, Image.SCALE_SMOOTH);
                        fondoGato = new ImageIcon(modificada);
                    }

                    String menu = "Opciones \n"
                            + "1. Ver Otra imagen \n"
                            + "2. Eliminar Favoritos \n"
                            + "3.Volver " +
                            " \n";

                    String [] botones = {"Ver otra imagen","Eliminar favorito", "Volver"};
                    String id_gato = gatosFav.getId();
                    String opciones = (String) JOptionPane.showInputDialog(null,
                            menu,
                            id_gato,
                            JOptionPane.INFORMATION_MESSAGE,
                            fondoGato,
                            botones,
                            botones[0]);


                    int selecion = -1;
                    for(int i=0;i< botones.length;i++){
                        if(opciones.equals(botones[i])){
                            selecion = i;
                        }
                    }

                    switch (selecion){
                        case 0:
                            verFavorito(apiKey);
                            break;
                        case 1:
                            borrarFavorito(gatosFav);
                            break;
                        default:
                            break;
                    }

                }catch (IOException e){
                    System.out.println("Ocurio un problema");
                    System.out.println(e);
                }

            }

        }catch (IOException e){
            System.out.println(e);
        }
    }

    public static  void borrarFavorito(GatosFavoritos gatofavorito){
        try {
            OkHttpClient client =  new OkHttpClient();
            Request request =  new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/"+gatofavorito.getId()+"")
                    .delete(null)
                    .addHeader("Content-Type","application/json")
                    .addHeader("x-api-key", gatofavorito.getApiKey())
                    .build();
            Response response = client.newCall(request).execute();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
