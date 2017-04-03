package dnd.furkhail.bonuscalculator.domain.repository;

import java.util.List;

import dnd.furkhail.bonuscalculator.domain.business.Status;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface StatusRepository {

    Maybe<List<Status>> addStatus(Status status);

    boolean removeStatus(String name);

    Observable<List<Status>> getStatusList();
}
