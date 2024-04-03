package com.example.generadordni;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController {

    @FXML
    private TextField nom;
    @FXML
    public TextField dni;
    @FXML
    private TextField cogNoms;
    @FXML
    private TextField multiplicador;
    @FXML
    public Button genDni;
    @FXML
    public Button borrDni;
    String numsString;
    private static final String REGEX_NOMBRE_APELLIDO = "^[\\p{L}ñÑáéíóúÁÉÍÓÚüäëïöÄËÏÖÜ]+(\\s*[\\p{L}ñÑáéíóúÁÉÍÓÚüÜäëïöÄËÏÖ]*)*[\\p{L}ñÑáéíóúÁÉÍÓÚüÜäëïöÄËÏÖ]+$";
    private static final Pattern PATTERN = Pattern.compile(REGEX_NOMBRE_APELLIDO);

    @FXML
    public void generarDNI() {
        String erroComprovacion = "El format no és el correcte";
        String errorLength = "El teu nom més el te cognom ha de ser igual o més gran a 7";

        if (comprovacions(nom)) {
            String nomFinal = nom.getText();
            if (nomFinal.length() >= 7) {
                String dniString;
                numsString = obtenirSetChars(nomFinal);
                dniString = numsString + crearDni(numsString);
                dni.setText(dniString);
            } else if (comprovacions(cogNoms)) {
                String cogNomComp = cogNoms.getText();
                String dniString;
                String nombreCompleto = nomFinal + cogNomComp;
                if (nombreCompleto.length() >= 7) {
                    numsString = obtenirSetChars(nombreCompleto);
                    dniString = numsString + crearDni(numsString);
                    dni.setText(dniString);
                } else {
                    showError(errorLength);
                }
            } else {
                showError(erroComprovacion);
            }
        } else {
            showError(erroComprovacion);
        }
    }
    @FXML
    protected boolean comprovacions(TextField textField) {
        String string = textField.getText().replaceAll("\\s", "");
        Matcher matcher = PATTERN.matcher(string);
        return matcher.matches();
    }
    @FXML
    protected String obtenirSetChars(String lletres) {
        String abecedari = "abcdefghijklmnñopqrstuvwxyzáéíóúàèìòùüäëïö ";
        StringBuilder numerosDNI = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            char lletra = lletres.charAt(i);
            int posicio = abecedari.indexOf(Character.toLowerCase(lletra));
            numerosDNI.append(posicio % 10);
        }
        while (numerosDNI.length() < 8) {
            numerosDNI.insert(0, '0');
        }

        return numerosDNI.toString();
    }

    @FXML
    protected char crearDni(String cadena) {
        String errorMulti = "El número ha d'estar entre 1 i 9";
        String error = "Algun format no ha sigut valid";
        try {
            int multiplicadorInt = Integer.parseInt(multiplicador.getText());
            if (multiplicadorInt > 1 && multiplicadorInt < 9){
                long num = Long.parseLong(cadena);
                String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
                return letras.charAt((int) (num % 23));
            }else{
                showError(errorMulti);
            }
        } catch (NumberFormatException e) {
            showError(error);
        }
        return ' ';
    }

    public void borrarDNI() {
        dni.clear();
    }
    @FXML
    protected void showError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Alert Dialog");
        alert.setContentText(error);
        alert.showAndWait();
    }


}
