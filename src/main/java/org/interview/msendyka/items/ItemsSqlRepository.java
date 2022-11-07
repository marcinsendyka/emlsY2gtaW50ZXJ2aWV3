package org.interview.msendyka.items;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ItemsSqlRepository extends CrudRepository<ItemEntity, String> {}
