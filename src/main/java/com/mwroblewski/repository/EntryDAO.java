package com.mwroblewski.repository;

import com.mwroblewski.entity.Entry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryDAO extends CrudRepository<Entry, Long> {
}
