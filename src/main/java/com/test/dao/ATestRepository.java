package com.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.modal.AppUserDto;

public interface ATestRepository extends JpaRepository<AppUserDto, Long> {

//	And -- findByLastnameAndFirstname (where x.lastname = ?1 and x.firstname = ?2)
//	
//	Or  -- findByLastnameOrFirstname (where x.lastname = ?1 or x.firstname = ?2)
//	
//	Not -- findByLastnameNot (where x.lastname <> ?1)
//
//	In -- findByAgeIn(Collection<Age> ages) (where x.age in ?1)
//
//	NotIn -- findByAgeNotIn(Collection<Age> age) (where x.age not in ?1)
//	
//	IsNull -- findByAgeIsNull (where x.age is null)
//
//	IsNotNull, NotNull -- findByAge(Is)NotNull (where x.age not null)
//	
//	Is,Equals -- findByFirstname,findByFirstnameIs,findByFirstnameEquals (where x.firstname = ?1)
//
//	Between -- findByStartDateBetween (where x.startDate between ?1 and ?2)
//
//	LessThan -- findByAgeLessThan (where x.age < ?1)
//
//	LessThanEqual -- findByAgeLessThanEqual (where x.age ⇐ ?1)
//
//	GreaterThan -- findByAgeGreaterThan (where x.age > ?1)
//
//	GreaterThanEqual -- findByAgeGreaterThanEqual (where x.age >= ?1)
//
//	After -- findByStartDateAfter (where x.startDate > ?1)
//
//	Before -- findByStartDateBefore (where x.startDate < ?1)
//
//	Like -- findByFirstnameLike (where x.firstname like ?1)
//
//	NotLike -- findByFirstnameNotLike (where x.firstname not like ?1)
//
//	StartingWith -- findByFirstnameStartingWith (where x.firstname like ?1 (parameter bound with appended %))
//
//	EndingWith -- findByFirstnameEndingWith (where x.firstname like ?1 (parameter bound with prepended %))
//
//	Containing -- findByFirstnameContaining (where x.firstname like ?1 (parameter bound wrapped in %))
//
//	OrderBy -- findByAgeOrderByLastnameDesc (where x.age = ?1 order by x.lastname desc)
//
//	True -- findByActiveTrue()(where x.active = true)
//
//	False -- findByActiveFalse() (where x.active = false)
//
//	IgnoreCase -- findByFirstnameIgnoreCase (where UPPER(x.firstame) = UPPER(?1))

// List<PersonDto> persons = personRepo.findAll(Sort.by("createdOn").descending());
}
