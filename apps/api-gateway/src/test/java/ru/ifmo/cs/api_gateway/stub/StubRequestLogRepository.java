package ru.ifmo.cs.api_gateway.stub;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import ru.ifmo.cs.api_gateway.request_log.entity.RequestLog;
import ru.ifmo.cs.api_gateway.request_log.repository.RequestLogRepository;

public class StubRequestLogRepository implements RequestLogRepository {
    @Override
    public void flush() {

    }

    @Override
    public <S extends RequestLog> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends RequestLog> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<RequestLog> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public RequestLog getOne(Long aLong) {
        return null;
    }

    @Override
    public RequestLog getById(Long aLong) {
        return null;
    }

    @Override
    public RequestLog getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends RequestLog> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends RequestLog> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends RequestLog> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends RequestLog> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends RequestLog> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends RequestLog> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends RequestLog, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends RequestLog> S save(S entity) {
        return null;
    }

    @Override
    public <S extends RequestLog> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<RequestLog> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<RequestLog> findAll() {
        return List.of();
    }

    @Override
    public List<RequestLog> findAllById(Iterable<Long> longs) {
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
    public void delete(RequestLog entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends RequestLog> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<RequestLog> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<RequestLog> findAll(Pageable pageable) {
        return null;
    }
}
