package com.library.app.category.repository;

import static com.library.app.commontests.category.CategoryForTestsRepository.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.library.app.category.model.Category;

public class CategoryRepositoryUTest {
	private EntityManagerFactory emf;
	private EntityManager em;
	private CategoryRepository categoryRepository;
	final static Logger logger = Logger.getLogger(CategoryRepositoryUTest.class);

	@Before
	public void initTestCase() {
		emf = Persistence.createEntityManagerFactory("libraryPU");
		em = emf.createEntityManager();

		categoryRepository = new CategoryRepository();
		categoryRepository.em = em;
		logger.info("------------->I'm starting");
	}

	@After
	public void closeEntityManager() {
		em.close();
		emf.close();
	}

	@Test
	public void addCategoryAndFindIt() {
		Long categoryAddedId = null;
		try {
			em.getTransaction().begin();
			categoryAddedId = categoryRepository.add(java()).getId();
			assertThat(categoryAddedId, is(notNullValue()));
			em.getTransaction().commit();
			em.clear();
			logger.info("------------->Added:" + categoryAddedId);
		} catch (final Exception e) {
			fail("This exception should not have been thrown");
			e.printStackTrace();
			em.getTransaction().rollback();
		}

		final Category category = categoryRepository.findById(categoryAddedId);
		assertThat(category, is(notNullValue()));
		assertThat(category.getName(), is(equalTo(java().getName())));
		logger.info("------------->Found:" + category.getId());
	}

}