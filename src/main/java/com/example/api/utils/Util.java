package com.example.api.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    public static byte[] toByte(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        return baos.toByteArray();
    }

    public static byte[] toJson(Object object) throws JsonProcessingException, UnsupportedEncodingException {
        return new ObjectMapper().writeValueAsString(object).getBytes("UTF-8");
    }

    public static String blobToString(Blob blob) throws SQLException {
        return new String(blob.getBytes(1, (int) blob.length()));
    }

    public static String getUIID() {
        return UUID.randomUUID().toString();
    }

    public static Date addMinutesToDate(int minutes, Date beforeTime) {
        final long MILLIS = 60000;

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * MILLIS));
        return afterAddingMins;
    }
}
