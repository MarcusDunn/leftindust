package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.dao.entity.superclasses.AbstractJpaPersistable
import com.leftindust.mockingbird.extensions.onUndefined
import com.leftindust.mockingbird.graphql.types.input.GraphQLVisitEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLVisitInput
import org.hibernate.Session
import javax.persistence.CascadeType
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.NamedAttributeNode
import javax.persistence.NamedEntityGraph
import javax.persistence.NamedSubgraph
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(columnNames = ["event_id"])]
)
@NamedEntityGraph(
    name = "Visit.event.patients",
    attributeNodes = [NamedAttributeNode("event", subgraph = "patients")],
    subgraphs = [
        NamedSubgraph(name = "patients", attributeNodes = [NamedAttributeNode("patients")])
    ]
)
class Visit(
    @OneToOne(cascade = [CascadeType.MERGE], optional = false)
    var event: Event,
    var title: String? = null,
    var description: String? = null,
    @ElementCollection
    // stored as URLS to the code
    var icds: Set<String> = emptySet(),
) : AbstractJpaPersistable() {
    fun setByGqlInput(graphQLVisitEditInput: GraphQLVisitEditInput, session: Session) {
        graphQLVisitEditInput.eid?.let { event = session.get(Event::class.java, it.id) }
        title = graphQLVisitEditInput.title.onUndefined(title)
        description = graphQLVisitEditInput.description.onUndefined(description)
        icds = graphQLVisitEditInput.foundationIcdCodes?.map { codeInput -> codeInput.url }?.toSet() ?: icds
    }

    constructor(visitInput: GraphQLVisitInput, event: Event) : this(
        event = event,
        title = visitInput.title,
        description = visitInput.description,
        icds = visitInput.foundationIcdCodes.map { it.url }.toSet(),
    )
}