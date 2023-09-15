package com.extension.spi.factory;


import com.extension.spi.anotatation.Activate;
import com.extension.spi.anotatation.SPI;
import com.extension.spi.extension.ExtensionLoader;
import org.apache.commons.lang3.StringUtils;

@Activate
public class SpiExtensionFactory implements ExtensionFactory {

    @Override
    public <T> T getExtension(final String key, final Class<T> clazz) {
        if (clazz.isInterface() && clazz.isAnnotationPresent(SPI.class)) {
            ExtensionLoader<T> extensionLoader = ExtensionLoader.getExtensionLoader(clazz);
            if (!extensionLoader.getSupportedExtensions().isEmpty()) {
               if(StringUtils.isBlank(key)){
                   return extensionLoader.getDefaultActivate();
               }
               return extensionLoader.getActivate(key);
            }
        }
        return null;
    }
}