package com.example;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Day04DigestMining {

    @SneakyThrows
    public static void main(String[] args) {

        var prefix = "ckczppom";

        MessageDigest md = MessageDigest.getInstance("MD5");

        var number = 0;
        var resultCount = 0;

        do {
            number += 1;
            md.reset();

            var dig = md.digest((prefix + number).getBytes(StandardCharsets.UTF_8));

            if ((resultCount & 0x1) == 0 && hasFiveLeadingZeros(dig)) {
                System.out.println("part 1 solution: " + number);
                resultCount |= 0x1;
            }

            if ((resultCount & 0x10) == 0 && hasSixLeadingZeros(dig)) {
                System.out.println("part 2 solution: " + number);
                resultCount |= 0x10;
            }

        } while (resultCount != 0x11);
    }

    private static boolean hasFiveLeadingZeros(byte[] d) {
        return d[0] == 0 && d[1] == 0 && (d[2] & 0xf0) == 0;
    }

    private static boolean hasSixLeadingZeros(byte[] d) {
        return d[0] == 0 && d[1] == 0 && d[2] == 0;
    }
}
