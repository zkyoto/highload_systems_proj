package ru.ifmo.cs.api_gateway.stub;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import ru.ifmo.cs.api_gateway.response_log.entity.ResponseLog;
import ru.ifmo.cs.api_gateway.response_log.repository.ResponseLogRepository;

public class StubResponseLogRepository implements ResponseLogRepository {
    @Override
    public void flush() {

    }

    @Override
    public <S extends ResponseLog> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ResponseLog> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<ResponseLog> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ResponseLog getOne(Long aLong) {
        return null;
    }

    @Override
    public ResponseLog getById(Long aLong) {
        return null;
    }

    @Override
    public ResponseLog getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends ResponseLog> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ResponseLog> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends ResponseLog> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends ResponseLog> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ResponseLog> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ResponseLog> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ResponseLog, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends ResponseLog> S save(S entity) {
        return null;
    }

    @Override
    public <S extends ResponseLog> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<ResponseLog> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<ResponseLog> findAll() {
        return List.of();
    }

    @Override
    public List<ResponseLog> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(ResponseLog entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends ResponseLog> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ResponseLog> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<ResponseLog> findAll(Pageable pageable) {
        return null;
    }
}
