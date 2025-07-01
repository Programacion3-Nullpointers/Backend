package com.jmq.inversiones.facturacion;


import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.dominio.ventas.Detalle;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.net.ssl.*;
import okhttp3.*;
import org.json.JSONObject;

public class FacturacionAPI {

    private final OkHttpClient client;
    private final MediaType mediaType;
    private RequestBody body;
    private Request request;

    public FacturacionAPI() {
        client = new OkHttpClient.Builder()
    .sslSocketFactory(getUnsafeSSLSocketFactory(), getUnsafeTrustManager())
    .hostnameVerifier((hostname, session) -> true)
    .build();
        mediaType = MediaType.parse("application/json");
    }

    private X509TrustManager getUnsafeTrustManager() {
        return new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[]{}; }
        };
    }

    private SSLSocketFactory getUnsafeSSLSocketFactory() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[]{}; }
                }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslContext.getSocketFactory();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void createBody(OrdenVenta orden) {

    String razonSocial = "Inversiones Industriales JMQ EIRL";
    String ruc = "20601355241";
    String domicilio = "Calle Rio La laguna";
    String urbanizacion = "Urbanizacion Los Angeles";
    List<Detalle> detalles = orden.getDetalle();

    Usuario usu = orden.getUsuario();
    
    // Formateadores
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    //SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

    // Variables String
    String fechaActual = formatoFecha.format(orden.getFecha_orden());
    //String horaActual = formatoHora.format(orden.getFecha_orden());
    
    
    double totalGravada = detalles.stream()
    .mapToDouble(d -> d.getCantidad() * d.getPrecio_unitario())
    .sum();
    double totalIGV = totalGravada * 0.18;

    
    //).append(codSunat.get(i)).append(
    StringBuilder itemsBuilder = new StringBuilder();
    for (int i = 0; i < detalles.size(); i++) {
        Detalle d = detalles.get(i);
        
        itemsBuilder.append("    {\n")
            .append("      \"producto\": \"").append(d.getProducto().getNombre()).append("\",\n")
            .append("      \"cantidad\": \"").append(d.getCantidad()).append("\",\n")
            .append("      \"precio_base\": \"").append(d.getPrecio_unitario()).append("\",\n")
            .append("      \"codigo_sunat\": \"00000000\",\n")
            .append("      \"codigo_producto\": \"").append(i).append("\",\n")
            .append("      \"codigo_unidad\": \"ZZ\",\n")
            .append("      \"tipo_igv_codigo\": \"10\"\n")
            .append("    }");

        if (i < detalles.size() - 1) {
            itemsBuilder.append(",\n");
        } else {
            itemsBuilder.append("\n");
        }
    }

    String json = "{\n" +
        "  \"empresa\": {\n" +
        "    \"ruc\": \"" + ruc + "\",\n" +
        "    \"razon_social\": \"" + razonSocial + "\",\n" +
        "    \"nombre_comercial\": \"FACTURACION INTEGRAL\",\n" +
        "    \"domicilio_fiscal\": \"" + domicilio + "\",\n" +
        "    \"ubigeo\": \"150114\",\n" +
        "    \"urbanizacion\": \"" + urbanizacion + "\",\n" +
        "    \"distrito\": \"ATE VITARTE\",\n" +
        "    \"provincia\": \"LIMA\",\n" +
        "    \"departamento\": \"LIMA\",\n" +
        "    \"modo\": \"0\",\n" +
        "    \"usu_secundario_produccion_user\": \"MODDATOS\",\n" +
        "    \"usu_secundario_produccion_password\": \"MODDATOS\"\n" +
        "  },\n" +
        "  \"cliente\": {\n" +
        "    \"razon_social_nombres\": \"" + usu.getRazonsocial() + "\",\n" +
        "    \"numero_documento\": \"" + usu.getRUC() + "\",\n" +
        "    \"codigo_tipo_entidad\": \"6\",\n" +
        "    \"cliente_direccion\": \"" + usu.getDireccion() + "\"\n" +
        "  },\n" +
        "  \"venta\": {\n" +
        "    \"serie\": \"FF03\",\n" +
        "    \"numero\": \"53953\",\n" +
        "    \"fecha_emision\": \"" + fechaActual + "\",\n" +
        "    \"hora_emision\": \"12:00:00\",\n" +
        "    \"fecha_vencimiento\": \"\",\n" +
        "    \"moneda_id\": \"1\",\n" +
        "    \"forma_pago_id\": \"1\",\n" +
        "    \"total_gravada\": \""+String.format("%.2f", totalGravada)+"\",\n" +
        "    \"total_igv\": \""+String.format("%.2f", totalIGV)+"\",\n" +
        "    \"total_exonerada\": \"\",\n" +
        "    \"total_inafecta\": \"\",\n" +
        "    \"tipo_documento_codigo\": \"01\",\n" +
        "    \"nota\": \"notas o comentarios\"\n" +
        "  },\n" +
        "  \"items\": [\n" +
        itemsBuilder.toString() +
        "  ]\n" +
        "}";

    body = RequestBody.create(json, mediaType);
}

    
    public void createRequest() {
        String apiUrl = "https://facturaciondirecta.com/API_SUNAT/post.php";

        request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
    }

    public String createResponse(OrdenVenta orden) throws IOException {
        createBody(orden);
        createRequest();

        // Desactiva la validación SSL (solo para pruebas)

        
        try (Response response = client.newCall(request).execute()) {
            System.out.println("Código HTTP: " + response.code());
            //System.out.println("Respuesta del servidor:\n" + response.body().string());
            
            // Tu cadena JSON (aquí está resumida para el ejemplo)
            String jsonString = response.body().string();
            
//            System.out.println("Respuesta completa:\n" + jsonString);

            // Convertir el string a un objeto JSONObject
            JSONObject jsonObject = new JSONObject(jsonString);

            // Verifica si hay un mensaje de error
            if (jsonObject.has("mensaje")) {
                System.out.println("Mensaje del servidor: " + jsonObject.getString("mensaje"));
            }

            if (jsonObject.has("error")) {
                System.out.println("Error del servidor: " + jsonObject.getString("error"));
            }

            if (jsonObject.has("success")) {
                System.out.println("Éxito: " + jsonObject.getBoolean("success"));
            }
            
            
            // Acceder al objeto "data"
            JSONObject data = jsonObject.getJSONObject("data");

            // Obtener el valor de "respuesta_sunat_descripcion"
            String descripcion = data.getString("ruta_pdf");

            // Imprimirlo
            return "Link Factura electronica: " + descripcion;
            
        } catch (Exception ex) {
            throw new RuntimeException("Error al ejecutar la facturación: " + ex.getMessage(), ex);
        }
    }
}
