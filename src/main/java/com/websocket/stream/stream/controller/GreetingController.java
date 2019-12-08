package com.websocket.stream.stream.controller;

import com.github.sarxos.webcam.Webcam;
import com.websocket.stream.stream.model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
public class GreetingController {

    protected Webcam webcam;

    @MessageMapping("/request")
    @SendTo("/topic/streamClient")
    public String greeting(HelloMessage message) throws Exception {
        if (webcam == null) {
            webcam = Webcam.getDefault();
            webcam.open();
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = webcam.getImage();
        ImageIO.write(bufferedImage, "png", Base64.getEncoder().wrap(stream));
        return stream.toString(StandardCharsets.ISO_8859_1.name());
    }
}
