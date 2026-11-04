package com.embobinados.apugen_backend.utilz;

public class StringUtilz {
    public static String limpiarParametro(String valor) {
        // Si es nulo o solo contiene espacios en blanco, retorna null.
        // De lo contrario, retorna el texto sin espacios accidentales a los lados.
        return (valor != null && !valor.isBlank()) ? valor.trim() : null;
    }

    public static String prepararFiltro(String valor, boolean exactMatch) {
        if (valor == null || valor.isBlank()) return null;
        String limpio = valor.trim();
        return exactMatch ? limpio : "%" + limpio + "%";
    }
}
