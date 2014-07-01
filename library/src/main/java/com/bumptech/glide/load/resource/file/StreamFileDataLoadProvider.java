package com.bumptech.glide.load.resource.file;

import com.bumptech.glide.DataLoadProvider;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.NullResourceEncoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class StreamFileDataLoadProvider implements DataLoadProvider<InputStream, File> {
    private static final ErrorSourceDecoder ERROR_DECODER = new ErrorSourceDecoder();

    private final ResourceDecoder<File, File> cacheDecoder;
    private final Encoder<InputStream> encoder;

    public StreamFileDataLoadProvider() {
        cacheDecoder = new FileDecoder();
        encoder = new StreamEncoder();
    }

    @Override
    public ResourceDecoder<File, File> getCacheDecoder() {
        return cacheDecoder;
    }

    @Override
    public ResourceDecoder<InputStream, File> getSourceDecoder() {
        return ERROR_DECODER;
    }

    @Override
    public Encoder<InputStream> getSourceEncoder() {
        return encoder;
    }

    @Override
    public ResourceEncoder<File> getEncoder() {
        return NullResourceEncoder.get();
    }

    private static class ErrorSourceDecoder implements ResourceDecoder<InputStream, File> {

        @Override
        public Resource<File> decode(InputStream source, int width, int height) throws IOException {
            throw new Error("You cannot decode a File from an InputStream by default, "
                    + "try either #cacheSource(true) to avoid this call or #decoder(ResourceDecoder) to replace this "
                    + "Decoder");
        }

        @Override
        public String getId() {
            return "";
        }
    }
}
