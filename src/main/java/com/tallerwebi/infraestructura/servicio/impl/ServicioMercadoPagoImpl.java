package com.tallerwebi.infraestructura.servicio.impl;
// SDK de Mercado Pago

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.infraestructura.servicio.ServicioMercadoPago;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
// Agrega credenciales

@Service
public class ServicioMercadoPagoImpl implements ServicioMercadoPago {
    @Override
    public String realizarPago(Long cantidad) {
        MercadoPagoConfig.setAccessToken("TEST-3343260709384493-112711-624fa476a769c2d2dd4586e78154be04-301994928");

        //Aca armamos el item que queremos que se muestre en mercadoPago
        PreferenceItemRequest itemRequest =
                PreferenceItemRequest.builder()
                        .title("Monedas")
                        .description("Monedas JavaDunk")
                        .pictureUrl("https://ibb.co/Rgq4JQ3")
                        .categoryId("games")
                        .quantity(100)
                        .currencyId("ARS")
                        .unitPrice(new BigDecimal("1"))
                        .build();
        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);


        /*
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items).build();
        */

        //Agregamos el Usuario de pago
        PreferencePayerRequest payer =
                PreferencePayerRequest.builder()
                        .name("test")
                        .email("test@unlam.edu.ar")
                        .build();

        //Agregamos los endpoints segun exito o no de la compra
        PreferenceBackUrlsRequest backUrls =
                PreferenceBackUrlsRequest.builder()
                        .success("http://localhost:8080/compraExitosa")
                        .failure("http://localhost:8080/compraDenegada")
                        .build();


        //Creamos el PreferenceRequest con toda la info
        // Items, usuario de pago, y las backsUrl
        PreferenceRequest request = PreferenceRequest.builder()
                .items(items)
                .payer(payer)
                .backUrls(backUrls)
                .build();

        PreferenceClient client = new PreferenceClient();
        //Hacemos el llamado a mercadopago para que nos devulva el initPoint
        Preference preference = new Preference();
        try {
            preference = client.create(request);
        } catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
        }

        return preference.getInitPoint();
    }
}
