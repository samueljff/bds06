package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("SELECT obj "
			+ "FROM Movie obj "
			+ "INNER JOIN obj.genre gen "
			+ "WHERE (COALESCE(:genres) IS NULL OR :genres IN gen) AND "
			+ "(obj.genre.id = :genreId OR :genreId = 0) "
			+ "ORDER by obj.title")
	Page<MovieDTO> find(List<Genre> genres, Long genreId, Pageable pageable);
}
