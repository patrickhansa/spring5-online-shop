package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.ProductCommand;
import com.ecommerce.spring5onlineshop.model.Product;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
public class ProductToProductCommand implements Converter<Product, ProductCommand> {

    private final CategoryToCategoryCommand categoryConverter;

    public ProductToProductCommand(CategoryToCategoryCommand categoryConverter) {
        this.categoryConverter = categoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public ProductCommand convert(Product source) {
        // Convert the Byte array that represents
        // the image to a byte array
        Byte[] byteObjects = source.getImage();
        byte[] bytes = new byte[byteObjects.length];
        int j = 0;

        for (Byte b : byteObjects) {
            bytes[j++] = b;
        }

        final ProductCommand productCommand = new ProductCommand();
        productCommand.setId(source.getId());
        productCommand.setName(source.getName());
        productCommand.setPrice(source.getPrice());
        productCommand.setStock(source.getStock());
        productCommand.setDescription(source.getDescription());
        productCommand.setImage(new DecodedMultipartFile(bytes));

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(category ->
                            productCommand.getCategories().add(categoryConverter.convert(category)));
        }

        return productCommand;
    }


    public static class DecodedMultipartFile implements MultipartFile {
        private final byte[] imgContent;

        public DecodedMultipartFile(byte[] imgContent) {
            this.imgContent = imgContent;
        }

        @Override
        public String getName() {
            // TODO - implementation depends on your requirements
            return null;
        }

        @Override
        public String getOriginalFilename() {
            // TODO - implementation depends on your requirements
            return null;
        }

        @Override
        public String getContentType() {
            // TODO - implementation depends on your requirements
            return null;
        }

        @Override
        public boolean isEmpty() {
            return imgContent == null || imgContent.length == 0;
        }

        @Override
        public long getSize() {
            return imgContent.length;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return imgContent;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(imgContent);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            new FileOutputStream(dest).write(imgContent);
        }
    }
}


