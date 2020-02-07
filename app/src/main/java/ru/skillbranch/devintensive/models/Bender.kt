package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {
    var schk: Int = 0

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        val validTrue = when (question) {
            Question.NAME -> answer[0] == answer[0].toUpperCase()
            Question.PROFESSION -> answer[0] == answer[0].toLowerCase()
            Question.MATERIAL -> answer.matches("^\\D*$".toRegex())
            Question.BDAY -> answer.matches("[0-9]*".toRegex())
            Question.SERIAL -> answer.matches("[0-9]*".toRegex()) && answer.length == 7
            Question.IDLE -> true
        }
        val str: String
        if (validTrue) {
            str = if (question.answers.contains(answer.toLowerCase())) {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}"
            }else if (question == Question.IDLE) {
                question.question
            } else {
                schk++
                if (schk == 3) {
                    status = Status.NORMAL
                    question = Question.NAME
                    schk = 0
                    "Это неправильный ответ. Давай все по новой\n${question.question}"
                } else {
                    status = status.nextStatus()
                    "Это неправильный ответ\n${question.question}"
                }
            }
        }else{
            str = "${question.validMes}\n${question.question}"
        }

        return str to status.color
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>, val validMes: String) {
        NAME(
            "Как меня зовут?",
            listOf("бендер", "bender"),
            "Имя должно начинаться с заглавной буквы"
        ) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION(
            "Назови мою профессию?",
            listOf("сгибальщик", "bender"),
            "Профессия должна начинаться со строчной буквы"
        ) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL(
            "Из чего я сделан?",
            listOf("металл", "дерево", "metal", "iron", "wood"),
            "Материал не должен содержать цифр"
        ) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY(
            "Когда меня создали?",
            listOf("2993"),
            "Год моего рождения должен содержать только цифры"
        ) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL(
            "Мой серийный номер?",
            listOf("2716057"),
            "Серийный номер содержит только цифры, и их 7"
        ) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf(), "") {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }
}