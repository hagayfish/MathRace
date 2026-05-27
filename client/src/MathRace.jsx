import React, { useState, useEffect } from 'react';
import './App.css'; // או כל קובץ עיצוב שיש לכם

function App() {
    const [difficulty, setDifficulty] = useState(1);
    const [questionData, setQuestionData] = useState(null); // עכשיו זה אובייקט, לא רק טקסט
    const [userAnswer, setUserAnswer] = useState('');
    const [feedback, setFeedback] = useState('');
    const [timeLeft, setTimeLeft] = useState(0);

    // 1. פונקציה למשיכת שאלה חדשה מהשרת
    const fetchQuestion = async () => {
        try {
            // שים לב: אנחנו מצפים לקבל בחזרה JSON חכם מהשרת
            const response = await fetch(`http://localhost:8080/get-question?difficulty=${difficulty}`);
            const data = await response.json();

            setQuestionData(data);
            setTimeLeft(data.timeLimit); // מעדכן את הטיימר לפי מה שהשרת החליט (למשל 15)
            setUserAnswer(''); // מאפס את תיבת הטקסט
            setFeedback('');   // מאפס את ההודעות הקודמות
        } catch (error) {
            setFeedback("שגיאה: לא הצלחנו להתחבר לשרת.");
            console.error(error);
        }
    };

    // 2. מנגנון הטיימר (יורד כל שנייה)
    useEffect(() => {
        // אם נשאר זמן ועוד לא ענינו
        if (timeLeft > 0 && !feedback) {
            const timerId = setTimeout(() => setTimeLeft(timeLeft - 1), 1000);
            return () => clearTimeout(timerId); // מנקה את הטיימר הקודם
        } else if (timeLeft === 0 && questionData && !feedback) {
            setFeedback("⏳ נגמר הזמן! נסה את השאלה הבאה.");
        }
    }, [timeLeft, questionData, feedback]);

    // 3. שליחת התשובה לבדיקה בשרת
    const submitAnswer = async () => {
        if (!userAnswer) return; // אם התלמיד לא הקליד כלום, אל תעשה כלום

        try {
            const response = await fetch(`http://localhost:8080/check-answer?questionId=${questionData.questionId}&answer=${userAnswer}`);
            const resultText = await response.text(); // השרת שלנו מחזיר כרגע טקסט רגיל (String)
            setFeedback(resultText);
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div style={{ textAlign: 'center', marginTop: '50px', direction: 'rtl' }}>
            <h1>🏎️ MathRace - מירוץ חשבון 🏎️</h1>

            <div style={{ marginBottom: '20px' }}>
                <label>בחר רמת קושי: </label>
                <select value={difficulty} onChange={(e) => setDifficulty(e.target.value)}>
                    <option value={1}>רמה 1 (קל)</option>
                    <option value={2}>רמה 2 (בינוני)</option>
                    <option value={3}>רמה 3 (קשה)</option>
                </select>
            </div>

            <button onClick={fetchQuestion} style={{ padding: '10px 20px', backgroundColor: '#4CAF50', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>
                {questionData ? "שאלה הבאה ➡️" : "התחל משחק!"}
            </button>

            {/* תצוגת השאלה והמשחק */}
            {questionData && (
                <div style={{ marginTop: '30px', padding: '20px', border: '2px solid #ccc', borderRadius: '10px', display: 'inline-block', minWidth: '300px' }}>

                    {/* שעון העצר */}
                    <h2 style={{ color: timeLeft <= 5 ? 'red' : 'black' }}>
                        ⏱️ זמן נותר: {timeLeft} שניות
                    </h2>

                    <h3 style={{ direction: /[א-ת]/.test(questionData.text) ? 'rtl' : 'ltr', display: 'inline-block' }}>
                        {questionData.text}
                    </h3>

                    {/* אזור הקלט והבדיקה (יוצג רק אם עוד לא נגמר הזמן ועוד לא קיבלנו פידבק) */}
                    {!feedback && timeLeft > 0 ? (
                        <div style={{ marginTop: '20px' }}>
                            <input
                                type="number"
                                value={userAnswer}
                                onChange={(e) => setUserAnswer(e.target.value)}
                                placeholder="הכנס תשובה..."
                                style={{ padding: '10px', fontSize: '16px', width: '120px', marginRight: '10px' }}
                            />
                            <button onClick={submitAnswer} style={{ padding: '10px 20px', backgroundColor: '#2196F3', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>
                                בדוק אותי!
                            </button>
                        </div>
                    ) : null}

                    {/* הודעת הפידבק לאחר התשובה */}
                    {feedback && (
                        <div style={{ marginTop: '20px', fontSize: '20px', fontWeight: 'bold' }}>
                            {feedback}
                        </div>
                    )}

                </div>
            )}
        </div>
    );
}

export default App;