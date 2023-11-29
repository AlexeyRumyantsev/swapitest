package ru.wondering.swtest.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Support localized numbers, like "234,546" for US locale
 * Please note current implementation handles only Number and Long class
 */
public class LocalizedDeserializationContext extends DefaultDeserializationContext {
    private final NumberFormat format;

    public LocalizedDeserializationContext(Locale locale) {
        this(BeanDeserializerFactory.instance, NumberFormat.getNumberInstance(locale));
    }

    private LocalizedDeserializationContext(DeserializerFactory factory, NumberFormat format) {
        super(factory, null);
        this.format = format;
    }

    private LocalizedDeserializationContext(DefaultDeserializationContext src, DeserializationConfig config, JsonParser parser, InjectableValues values, NumberFormat format) {
        super(src, config, parser, values);
        this.format = format;
    }

    @Override
    public DefaultDeserializationContext with(DeserializerFactory factory) {
        return new LocalizedDeserializationContext(factory, format);
    }

    @Override
    public DefaultDeserializationContext createInstance(DeserializationConfig config, JsonParser parser, InjectableValues values) {
        return new LocalizedDeserializationContext(this, config, parser, values, format);
    }

    @Override
    public DefaultDeserializationContext createDummyInstance(DeserializationConfig config) {
        return new LocalizedDeserializationContext(this, config, null, null, format);
    }

    @Override
    public Object handleWeirdStringValue(Class<?> targetClass, String value, String msg, Object... msgArgs) throws IOException {
        // This method is called when default deserialization fails.
        if (targetClass == Number.class) {
            return parseNumber(value);
        } else if (targetClass == Long.class) {
            return parseNumber(value).longValue();
        }
        return super.handleWeirdStringValue(targetClass, value, msg, msgArgs);
    }

    // It's synchronized because `NumberFormat` isn't thread-safe.
    private synchronized Number parseNumber(String value) throws IOException {
        try {
            return format.parse(value);
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}