package bo.roman.tinnitus.ui.dao;

import java.util.Optional;

public interface PropertiesDao<T> {
	Optional<T> read();
	void write(T t);
}