package com.springbatch.simplepartitionerlocal.batch.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class CustomArquivoReader<T> implements ItemStreamReader<T> {

    private FlatFileItemReader<T> delegate;
    private int itensLimit;

    public CustomArquivoReader(FlatFileItemReader<T> delegate, int itensLimit) {
        this.delegate = delegate;
        this.itensLimit = itensLimit;
    }

    @Override
    @Nullable
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (itensLimit > 0) {
            itensLimit--;
            return delegate.read();
        } else {
            return null;
        }
    }

    @Override
    public void update(@NonNull ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void open(@NonNull ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }

}
