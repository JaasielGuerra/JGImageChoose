/*
 *Controlador para el componente
 */
package com.guerra.jgimagechoose;

import com.guerra.jgimagechoose.view.View;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
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

    //imagen auxiliar para setear por defecto con el boton eliminar
    private Icon imageAux;

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
        this.fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.png, *.jpg, *.jpeg",
                "jpg", "png", "jpeg", "JPG", "JPEG", "PNG"));

        //escucha de eventos al boton
        this.btnSeleccionar.addActionListener(event -> seleccionarImagen());
        this.btnEliminar.addActionListener(event -> eliminarImagen());
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
     * Darle un tamanio al label de la imagen.
     *
     * <p>
     * Por defecto el tamanio de la imagen sera 200 x 200.
     * <p>
     *
     * @param width su ancho
     * @param height su algo
     */
    public void setImagePreferredSize(int width, int height) {
        this.lblImage.setPreferredSize(new Dimension(width, height));
    }

    /**
     * Devuelve la extension del archivo seleccionado.
     * <p>
     * Si el usuario no ha seleccionado nada, devolvera null.
     *
     * @return String de la extension.
     * <p>
     * Null en caso de no existir.
     */
    public String getExtensionFile() {
        if (fileChooser.getSelectedFile() != null) {
            return getExtension(fileChooser.getSelectedFile());
        } else {
            return null;
        }
    }

    /**
     * Asigna un texto al boton.
     *
     * @param text El texto a asignar
     */
    public void setTextButtonSelect(String text) {
        this.btnSeleccionar.setText(text);
    }

    /**
     * Devuelve el texto del boton.
     *
     * @return String del texto.
     */
    public String getTextButtonSelect() {
        return this.btnSeleccionar.getText();
    }

    /**
     * Asigna un icono al boton.
     *
     * @param icon El icono a asignar
     */
    public void setIconButtonSelect(ImageIcon icon) {
        this.btnSeleccionar.setIcon(icon);
    }

    /**
     * Asigna un icono al boton.
     *
     * @param icon El icono a asignar
     */
    public void setIconButtonDelete(ImageIcon icon) {
        this.btnEliminar.setIcon(icon);
    }

    /**
     * Devuelve la ruta de la imagen seleccionada por el usuario.
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
            this.lblImage.setIcon(new ImageIcon(img.getScaledInstance(lblImage.getPreferredSize().width,
                    lblImage.getPreferredSize().height, Image.SCALE_DEFAULT)));
            this.imageAux = lblImage.getIcon();//salvar imagen auxiliar

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen.\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Devuelve la imagen seleccionada por el usuario.
     * <p>
     * En caso de no haber seleccionado una imagen, entonces devolvera la imagen
     * que se haya asignado con el metodo <code>setImageIcon</code>.
     *
     * Si no se asigno o selecciono ninguna imagen, entonces devolvera null.
     *
     * @return imageIcon (imagen asignada o seleccionada).
     * <p>
     * Null en caso de no existir.
     */
    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    /**
     * Asignar una imagen al componente.
     *
     * @param imageIcon La imagen a setear.
     */
    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;

        this.lblImage.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(lblImage.getPreferredSize().width,
                lblImage.getPreferredSize().height, Image.SCALE_DEFAULT)));
        this.imageAux = lblImage.getIcon();//salvar imagen auxiliar
    }

    /**
     * Devuelve los bytes de la imagen seleccionada por el usuario.
     * <p>
     * En caso de no haber seleccionado una imagen, entonces devolvera los bytes
     * de la imagen que se hayan asignado con el metodo
     * <code>setImageByte</code>.
     *
     * Si no se asigno o selecciono ninguna imagen, entonces devolvera null.
     *
     * @return byte[] (imagen asignada o seleccionada).
     * <p>
     * Null en caso de no existir.
     */
    public byte[] getImageByte() {
        return imageByte;
    }

    /**
     * Asignar una imagen en bytes.
     *
     * @param imageByte Los bytes de la imagen.
     */
    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
        try {

            //flujo de entrada de datos tipo byte[]
            ByteArrayInputStream input = new ByteArrayInputStream(imageByte);

            //crear buffer a partir del flujo de entrada
            BufferedImage image = ImageIO.read(input);

            //setear la imagen en la vista, escalandola al tamanio del label
            this.lblImage.setIcon(new ImageIcon(image.getScaledInstance(lblImage.getPreferredSize().width,
                    lblImage.getPreferredSize().height, Image.SCALE_DEFAULT)));
            this.imageAux = lblImage.getIcon();//salvar imagen auxiliar

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al crear la imagen.\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    ////////////////////eventos//////////////
    private void seleccionarImagen() {//abre cuadro de dialogo para seleccionar imagen

        int estado = fileChooser.showOpenDialog(this);//abrir dialogo

        if (fileChooser.getSelectedFile() != null && estado == JFileChooser.APPROVE_OPTION) {

            try {

                //crear un buffer en memoria del archivo de imagen selecionado
                BufferedImage img = ImageIO.read(fileChooser.getSelectedFile());

                //setear la imagen en la vista, escalandola al tamanio del label
                lblImage.setIcon(new ImageIcon(img.getScaledInstance(lblImage.getPreferredSize().width,
                        lblImage.getPreferredSize().height, Image.SCALE_DEFAULT)));

                System.out.println("\033[36m ================================================================================");

                System.out.println("\033[32m JGImageChoose: Vista previa generada.");

                //guardarlo en los atributos
                this.imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                System.out.println("\033[32m JGImageChoose: Ruta de la imagen seteada\n\t\t => \033[32m" + fileChooser.getSelectedFile().getAbsolutePath());

                this.imageIcon = new ImageIcon(img);
                System.out.println("\033[32m JGImageChoose: objeto ImageIcon de la imagen seteado.");

                //flujo de salida de datos en bytes[]
                ByteArrayOutputStream output = new ByteArrayOutputStream();

                // escribir la imagen en el flujo de salida de datos
                ImageIO.write(img, getExtension(fileChooser.getSelectedFile()), output);

                this.imageByte = output.toByteArray();// obtener los bytes

                System.out.println("\033[32m JGImageChoose: datos bytes de la imagen seteados (extension = " + getExtension(fileChooser.getSelectedFile()) + ")");

                System.out.println("\033[36m ================================================================================");
                System.out.println("\033[32m TODO LISTO!.");
                System.out.println("\033[36m ================================================================================");

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen.\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    //eliminar la imagen y poner la que estaba por defecto
    private void eliminarImagen() {

        if (imageAux != null) {//si se asigno una pues se usa esa

            this.lblImage.setIcon(imageAux);

        } else {//sino pues se usa una predefinida

            this.lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-multiplicar-100.png")));
        }
    }

}
