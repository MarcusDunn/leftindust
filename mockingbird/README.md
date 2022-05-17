# Architecture



# Code review checklist
In order of most important to the least important

## Security

- [ ] *All* authorization is done at the service layer
  - Reason: `Service`'s are the finest grained interfaces we have
  - Exceptions: Some interfaces do not need securing, these are few and far between.

## Architecture

- [ ] All interactions with the service layer are done through domain objects or minimal interfaces.
  - Reason: The domain layer should not have to consider transport or persistence implementation.
  - Exception: Entities are often used as both a Domain Object and a Persistence Implementation detail. This is to taste and if there is *any* non-persistence behavior associated with an entity or if the entity has *no role* in the domain context (eg. `PatientDoctorEntity`), it should sole a persistence entity and perhaps have a domain entity .  
- [ ] No default arguments for POJO's or entities. 
  - Reason: We want the compiler to tell us where we are missing passing a potentially important field.
  - Exceptions: Tasteful use of defaults has its merits, but consider it as turning off a compiler feature for some syntactic sugar.

## Naming conventions

- [ ] Anything that is exposed via the schema should end in `Dto` (Data Transfer Object)
    - Reason: reminds everyone that everything in a DTO should be serializable
    - Exceptions:
- [ ] All domain objects should simply be their name. (eg. Doctor)
    - Reason: The shortest names should be reserved for the core data model
    - Exceptions:
- [ ] If there is a need to separate the persistence of a domain object from the domain object itself, the entity class
  should end in `Entity`
    - Reason: `Entity` is just a name that was chosen, sometimes it's nice to separate persistence from the domain
      model, although we mostly get away with keeping them the same.
    - Exceptions:
- [ ] No abbreviations. Use patientId instead of pid both for parameters and method names like
  getByPatientId
    - Reason: Sometimes there is conflicts, does the `p` stand for is it patient or practitioner?
    - Exceptions: If the abbreviation is common within the domain (`id`, `dto`, `icd`)
- [ ] Keep CRUD operations using Create Read Update Delete, don't use `Edit`, `Remove`, `Persist` as it makes
    - Reason: permissions using `hasAuthority` could potentially be confusing + general consistency.
    - Exceptions: `get` where idiomatic is alright for method names that do not have security associated with them. 
