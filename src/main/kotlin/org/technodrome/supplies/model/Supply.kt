package org.technodrome.supplies.model

import javax.persistence.*


@Entity
@Table(uniqueConstraints=arrayOf(UniqueConstraint(columnNames=arrayOf("name"))))
data class Supply(

        @Column(nullable = false )
        var name: String = "",

        @Column(nullable = false)
        var amount: Float = 0f,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0

)

