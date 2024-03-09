var currentQuestion = 1;
var points = 0;

function checkAnswer(questionNumber) {
    var correctAnswers = {
        1: 'b', // Ohm (Ω)
        2: 'b', // La batterie
        3: 'a', // P = U x I
        4: 'b', // Ampère (A)
        5: 'b', // Ampère (A)
        6: 'b', // Condensateur
        7: 'b', // Diode
        8: 'c', // Transformateur
        9: 'a', // Ampèremètre
        10: 'b', // Diminution du courant
        11: 'c', // 75 watts
        12: 'b', // 4 ampères
        13: 'c', // 6 ampères
        14: 'd', // 12 ohms
        15: 'b' // Diminution du courant
    };

        var selectedAnswer = document.querySelector('input[name="question' + questionNumber + '"]:checked');
        if (selectedAnswer && selectedAnswer.value === correctAnswers[questionNumber]) {
            points++;
            document.getElementById('result' + questionNumber).innerHTML = 'Bonne réponse !';
            document.getElementById('result' + questionNumber).classList.add('text-success');
        } else {
            document.getElementById('result' + questionNumber).innerHTML = 'Mauvaise réponse. La bonne réponse était: ' + correctAnswers[questionNumber];
            document.getElementById('result' + questionNumber).classList.add('text-danger');
        }

        document.getElementById('result' + questionNumber).style.display = 'block';

        if (questionNumber === 15) {
            showFinalResults();
        } else {
            document.getElementById('nextButtonContainer').style.display = 'block';
        }
    }

    function nextQuestion() {
        document.getElementById('question' + currentQuestion).style.display = 'none';
        document.getElementById('nextButtonContainer').style.display = 'none';

        if (currentQuestion < 15) {
            currentQuestion++;
            document.getElementById('question' + currentQuestion).style.display = 'block';
        } else {
            showFinalResults();
        }
    }

    function showFinalResults() {
        document.getElementById('score').innerText = 'Score: ' + points + ' / 15';
        document.getElementById('correctAnswersCount').innerText = 'Nombre de bonnes réponses: ' + points;
        document.getElementById('finalResults').style.display = 'block';
    }
    