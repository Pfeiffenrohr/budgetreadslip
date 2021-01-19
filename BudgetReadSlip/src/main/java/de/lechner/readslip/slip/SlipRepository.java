package de.lechner.readslip.slip;

import org.springframework.data.repository.CrudRepository;

//import de.lechner.cbudgetserver.transaction.Transaction;

public interface SlipRepository extends CrudRepository<Bon, Integer> {

}