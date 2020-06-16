/*
 *Controlador para el componente
 */
package com.guerra.jgimagechoose;

import com.guerra.jgimagechoose.view.View;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Jaasiel
 */
public class JGImageChoose extends View {

    private JFileChooser fileChooser;

    //imagen a devolver
    private String imagePath;
    private ImageIcon imageIcon;
    private byte[] imageByte;

    public JGImageChoose() {
        init();
    }

    private void init() {

        this.fileChooser = new JFileChooser();

        // solo archivos
        this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        //para filtrar solo imagenes png y jpg
        this.fileChooser.setAcceptAllFileFilterUsed(false);// primero remover cualquier filtro

        // segundo asginar un filtro por nombre de extension de archivo
        this.fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.png, *.jpg", "jpg", "png"));

        //escucha de eventos al boton
        this.btnSeleccionar.addActionListener(event -> seleccionarImagen());

    }

    //obtener la extension del archivo
    private String getExtension(File file) {

        String format = "none";
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");// obtener el indice del punto
        if (index > 0) {
            format = fileName.substring(index + 1);// extraer substring de la extension
            format = format.toLowerCase();// a minusculas
        }

        return format;
    }

    /////////////metodos getter y setter//////////////////
    /**
     * Asigna un texto al boton.
     *
     * @param text El texto a asignar
     */
    public void setTextButton(String text) {
        this.btnSeleccionar.setText(text);
    }

    /**
     * Devuelve el texto del boton.
     *
     * @return String del texto.
     */
    public String getTextButton() {
        return this.btnSeleccionar.getText();
    }

    /**
     * Devuelve la ruta de la imagen selecciona por el usuario.
     * <p>
     * En caso de no haber seleccionado una imagen, entonces devolvera la ruta
     * de la imagen que se haya asignado con el metodo
     * <code>setImagePath</code>.
     *
     * Si no se asigno o selecciono ninguna imagen, entonces devolvera null.
     *
     * @return String (imagen asignada o seleccionada).
     * <p>
     * Null en caso de no existir.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Asignar una imagen al componente.
     *
     * @param imagePath La ruta de la imagen.
     */
    public void setImagePath(String imagePath) {

        this.imagePath = imagePath;

        try {

            //crear un buffer en memoria del archivo de imagen selecionado
            BufferedImage img = ImageIO.read(new File(imagePath));

            //setear la imagen en la vista, escalandola al tamanio del label
            lblImage.setIcon(new ImageIcon(img.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_DEFAULT)));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen.\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    ////////////////////eventos//////////////
    private void seleccionarImagen() {//abre cuadro de dialogo para seleccionar imagen

        fileChooser.showOpenDialog(this);//abrir dialogo

        try {
            //flujo de salida de datos en bytes[]
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            //crear un buffer en memoria del archivo de imagen selecionado
            BufferedImage img = ImageIO.read(fileChooser.getSelectedFile());

            //setear la imagen en la vista, escalandola al tamanio del label
            lblImage.setIcon(new ImageIcon(img.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_DEFAULT)));
            System.out.println("\033[36m ================================================================================");

            System.out.println("\033[32m JGImageChoose: Vista previa generada.");

            //guardarlo en los atributos
            this.imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("\033[32m JGImageChoose: Ruta de la imagen seteada\n\t\t => \033[32m" + fileChooser.getSelectedFile().getAbsolutePath());

            this.imageIcon = new ImageIcon(img);
            System.out.println("\033[32m JGImageChoose: objeto ImageIcon de la imagen seteado.");

            // escribir la imagen en el flujo de salida de datos
            ImageIO.write(img, getExtension(fileChooser.getSelectedFile()), output);

            this.imageByte = output.toByteArray();// obtener los bytes
            System.out.println("\033[32m JGImageChoose: datos bytes de la imagen seteados.");
            
            System.out.println("\033[36m ================================================================================");
            System.out.println("\033[32m TODO LISTO!.");
            System.out.println("\033[36m ================================================================================");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen.\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
