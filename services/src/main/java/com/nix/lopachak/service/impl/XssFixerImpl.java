package com.nix.lopachak.service.impl;


import com.nix.lopachak.service.XssFixer;
import org.springframework.stereotype.Service;

/**
 * Class содержит метд для защиты от Xss - атак
 *
 * @author Volodymyr Lopachak
 * @version 1.0 01 October 2021
 */
@Service
public class XssFixerImpl implements XssFixer {

    /**
     * Метод заменяет значения "<" и ">" на альтернативные
     *
     * @param content - строковое представление поля
     * @return - модифицированную строку
     */
    @Override
    public String fix(String content) {
        return content.replace("<", "&lt;").replace(">", "&gt;");
    }
}
