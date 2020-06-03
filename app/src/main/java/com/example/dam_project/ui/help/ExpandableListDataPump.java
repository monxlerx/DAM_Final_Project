package com.example.dam_project.ui.help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        //Creating the content of expandable list (question and answer)
        List<String> navigationMenu = new ArrayList<String>();
        navigationMenu.add("Al iniciar la aplicación aparece un menú dividido en seis categorías (ensaladas, entrantes, carnes, pescado, postres y bebidas. Puedes acceder al listado de cada uno de ellos presionando en una categoría. " +
                "Si queremos entrar en detalle, bastará con volver a presionar y podemos acceder a las características del mismo. ");

        List<String> sendFeecback = new ArrayList<String>();
        sendFeecback.add("Estamos encantados de recibir vuestras críticas o halagos. Queremos ser los mejores y necesitamos de tu ayuda para ello. Cuéntanos lo que quieras.");

        List<String> favourites = new ArrayList<String>();
        favourites.add("Por supuesto que si.La idea de almacenar los platos del menú, es que, cuando vayas navegando entre las distintas categorías, puedas simplemente presionado el botón del corazón, añadir esos platos a " +
                "tu lista de favoritos. Es una forma sencilla de ir añadiendo lo que quieres y recordarlo en un futuro. Porque sabemos que volverás una y otra vez.");

        List<String> sharingApp = new ArrayList<String>();
        sharingApp.add("Puedes hacerlo desde el apartado Ayuda > Compartir. Existen varias formas para ello. Puedes utilizar un código QR, enviar un enlace por WhatsApp, Instagram o Gmail. Si ninguna de estas opciones es la deseada " +
                "podemos presionar sobre Otros, así podemos elegir entre nuemerosas opciones que ofrece Android.");


        expandableListDetail.put("¿Cómo desplazarse por el menú?", navigationMenu);
        expandableListDetail.put("¿Puedo guardar mis platos favoritos?", favourites);
        expandableListDetail.put("¿Existe alguna forma de enviar comentarios o quejas?", sendFeecback);
        expandableListDetail.put("¿Cómo puedes compartir la aplicación?", sharingApp);
        return expandableListDetail;
    }
}
