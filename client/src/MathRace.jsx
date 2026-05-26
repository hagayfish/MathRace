import React, { useState, useEffect } from 'react';
import axios from 'axios';

const MathRace = () => {
    const [question, setQuestion] = useState('לחץ על התחלת משחק כדי לקבל שאלה');
    const [difficulty, setDifficulty] = useState(1); // ברירת מחדל: רמה 1 (קל)
    const [loading, setLoading] = useState(false);

    // פונקציה שפונה לשרת ה-Java ומביאה שאלה חדשה
    const fetchQuestion = async (selectedDifficulty) => {
        setLoading(true);
        try {
            // שים לב: זו בדיוק הכתובת של השרת שפתחנו ב-Java!
            const response = await axios.get(`http://localhost:8080/api/math/generate?difficulty=${selectedDifficulty}`);
            setQuestion(response.data);
        } catch (error) {
            console.error("שגיאה בחיבור לשרת הבקאנד:", error);
            setQuestion("אופס, לא הצלחנו לתקשר עם השרת. ודא ששרת ה-Java מופעל!");
        }
        setLoading(false);
    };

    // שינוי רמה מהדרופדאון יקרא מיד לשאלה חדשה
    const handleDifficultyChange = (e) => {
        const newDifficulty = parseInt(e.target.value);
        setDifficulty(newDifficulty);
        fetchQuestion(newDifficulty);
    };

    return (
        <div style={{ textAlign: 'center', padding: '50px', fontFamily: 'Arial' }}>
            <h1>🏎️ מירוץ חשבון - MathRace 🏎️</h1>

            <div style={{ margin: '20px' }}>
                <label style={{ fontSize: '18px', marginLeft: '10px' }}>בחר רמת קושי: </label>
                <select value={difficulty} onChange={handleDifficultyChange} style={{ padding: '5px', fontSize: '16px' }}>
                    <option value={1}>רמה 1 (קל - חיבור/חיסור)</option>
                    <option value={2}>רמה 2 (בינוני - מספרים גדולים וכפל)</option>
                    <option value={3}>רמה 3 (קשה - תרגילים מתקדמים)</option>
                </select>
            </div>

            <div style={{ background: '#f0f0f0', padding: '30px', borderRadius: '10px', display: 'inline-block', minWidth: '300px', margin: '20px' }}>
                <h2 style={{ fontSize: '32px', color: '#333' }}>
                    {loading ? "מגריל שאלה..." : question}
                </h2>
            </div>

            <br />
            <button
                onClick={() => fetchQuestion(difficulty)}
                style={{ padding: '10px 20px', fontSize: '18px', backgroundColor: '#4CAF50', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' }}
            >
                שאלה הבאה ➡️
            </button>
        </div>
    );
};

export default MathRace;