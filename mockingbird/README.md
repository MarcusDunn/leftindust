# Architecture

We follow a pretty simple [layered architecture](https://www.baeldung.com/cs/layered-architecture).

The first layer is the application layer and consists of anything that gets exposed to the frontend. This contains a lot
of `@Controller`'s and `Dto`'s. This layer handles turning what looks like a nice api for a client to consume into
something our service layer can work with.

The second layer is the service layer, this is where most of the magic happens. This will contain most of our meaty
`@Services` and contains all of our authentication logic. This should be considered a pretty *pure* layer where it
should not have to concern itself with serialization or persistence details - instead just having logic around what it
needs to do. A *true OOP* approach would have us put a lot of the logic you find here into objects with state and data,
but I've chosen (for
better or worse) to keep behaviour and data as separate as possible.

The final layer is the persistence / external layer this is mostly `@Entity`'s and `@Repository` annotated classes. This
handles mapping from the service layer to the tables in the database as well as object lifecycle. This is also where we
put classes that have to talk to external services such as the ICD api.

# Configuration

Development runtime configuration should be done entirely inside `application.yml` you can add extra configuration
parameters via classes annotated with `@ConfigurationProperties`. Be sure to document them
in `META-INF/additional-spring-configuration-metadata.json`.

Deployment configuration is done through environment variables. See `docker-compose.yml` for details.

# Running

The backend needs a database to run (with its details passed through `application.yml`) and ideally also has an ICD api
running (where to find the api should also be set in `application.yml`) these can both be run as docker containers.

## ICD API

```bash
docker run -e "acceptLicense=true" -e "saveAnalytics=true" -e "include=2021-05_en" -p "80:80" whoicd/icd-api
```

## Postgresql Database

```bash
docker run -e "POSTGRES_USER=mediq" -e "POSTGRES_PASSWORD=mediq" -p "5432:5432" postgres
```

# Code review checklist

In order of most important to the least important

## Security

- [ ] *All* authorization is done at the service layer
    - Reason: `Service`'s are the finest grained interfaces we have
    - Exceptions: Some interfaces do not need securing, these are few and far between.

## Architecture

- [ ] All interactions with the service layer are done through domain objects or minimal interfaces.
    - Reason: The domain layer should not have to consider transport or persistence implementation.
    - Exception: Entities are often used as both a Domain Object and a Persistence Implementation detail. This is to
      taste and if there is *any* non-persistence behavior associated with an entity or if the entity has *no role* in
      the domain context (eg. `PatientDoctorEntity`), it should sole a persistence entity and perhaps have a domain
      entity .
- [ ] No default arguments for POJO's or entities.
    - Reason: We want the compiler to tell us where we are missing passing a potentially important field.
    - Exceptions: Tasteful use of defaults has its merits, but consider it as turning off a compiler feature for some
      syntactic sugar.

## Naming conventions

- [ ] Anything that is exposed via the schema should end in `Dto` (Data Transfer Object)
    - Reason: reminds everyone that everything in a DTO should be serializable
    - Exceptions: Simple Enum classes can be used throughout all layers.
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
