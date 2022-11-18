package io.gryteck.personservice.domain

object PersonEntityMother {
    fun aidana() = PersonEntityBuilder().apply {
        id = 1
        name = "Aidana"
        age = 19
        address = "Omsk, Russia"
        work = "MOPK"
    }
    fun kuban() = PersonEntityBuilder().apply {
        id = 1
        name = "Kuban"
        age = 30
        address = "Bishkek, Kyrgyzstan"
        work = "Boeing"
    }
    fun carl() = PersonEntityBuilder().apply {
        id = 1
        name = "Carl"
        age = 21
        address = "Shymkent, Kazakhstan"
        work = "Qasqyr"
    }

    fun multiple() = listOf(aidana(), kuban(), carl()).mapIndexed { i, person ->
        person.apply { id = i + 1 }
    }
}

class PersonEntityBuilder: Builder<PersonEntity> {
    var id: Int = 0
    var name: String = ""
    var age: Int? = null
    var address: String? = null
    var work: String? = null

    override fun build() = PersonEntity(
        id = id,
        name = name,
        age = age,
        address = address,
        work = work
    )
}