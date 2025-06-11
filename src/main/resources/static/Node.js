const cors = require('cors');
app.use(cors());


// app.js — 只用 app 物件，不用 router 分檔，所有 API 都集中這裡

const express = require('express');
const app = express();
app.use(express.json());
const mysql = require('mysql2/promise');
const cors = require('cors');

app.use(cors()); // 如果有前後端分離建議加這行

// 資料庫連線
const db = mysql.createPool({
    host: 'localhost',
    user: 'root',
    password: '', // <-- 請改成你自己的密碼
    database: 'petpetforum'        // <-- 請改成你自己的資料庫名
});

// 取得單篇貼文
app.get('/api/posts/:id', async (req, res) => {
    const postId = req.params.id;
    try {
        const [rows] = await db.query(`
            SELECT p.*, u.username
            FROM posts p
            JOIN users u ON p.user_id = u.id
            WHERE p.id = ?
        `, [postId]);
        if (!rows.length) return res.status(404).json({ error: 'Post not found' });
        res.json(rows[0]);
    } catch (err) {
        res.status(500).json({ error: 'Server error' });
    }
});

// 編輯貼文 (更新)
app.put('/api/posts/:id', async (req, res) => {
    const postId = req.params.id;
    const { title, content, category, imageUrl, videoUrl } = req.body;
    try {
        await db.execute(
            `UPDATE posts 
             SET title=?, content=?, category=?, image_url=?, video_url=?, updated_at=NOW()
             WHERE id=?`,
            [title, content, category, imageUrl, videoUrl, postId]
        );
        res.sendStatus(200);
    } catch (err) {
        res.status(500).send('更新失敗');
    }
});

// 刪除貼文
app.delete('/api/posts/:id', async (req, res) => {
    const postId = req.params.id;
    try {
        await db.execute('DELETE FROM posts WHERE id = ?', [postId]);
        res.sendStatus(200);
    } catch (err) {
        res.status(500).send('刪除失敗');
    }
});

// 收藏 API
app.post('/api/favorite', async (req, res) => {
    const { userId, postId } = req.body;
    try {
        await db.execute('INSERT INTO favorites (user_id, post_id) VALUES (?, ?)', [userId, postId]);
        res.sendStatus(200);
    } catch (err) {
        res.status(500).send('收藏失敗');
    }
});

app.delete('/api/favorite', async (req, res) => {
    const { userId, postId } = req.body;
    try {
        await db.execute('DELETE FROM favorites WHERE user_id = ? AND post_id = ?', [userId, postId]);
        res.sendStatus(200);
    } catch (err) {
        res.status(500).send('取消收藏失敗');
    }
});

// 按讚 API
app.post('/api/like', async (req, res) => {
    const { userId, postId } = req.body;
    try {
        await db.execute('INSERT INTO likes (user_id, post_id) VALUES (?, ?)', [userId, postId]);
        res.sendStatus(200);
    } catch (err) {
        res.status(500).send('按讚失敗');
    }
});

app.delete('/api/like', async (req, res) => {
    const { userId, postId } = req.body;
    try {
        await db.execute('DELETE FROM likes WHERE user_id = ? AND post_id = ?', [userId, postId]);
        res.sendStatus(200);
    } catch (err) {
        res.status(500).send('取消按讚失敗');
    }
});

// 你可以在這裡再加其他 API...

// 啟動伺服器
app.listen(3000, () => {
    console.log('Server running on port 3000');
});


const db = require('../db'); // 這是mysql連線

// 取得新聞列表
exports.getNewsList = (req, res) => {
    db.query('SELECT id, title, subtitle, image, date FROM news ORDER BY date DESC', (err, results) => {
        if (err) return res.status(500).json({error: '資料庫錯誤'});
        res.json(results);
    });
};

// 取得單一新聞＋相關新聞
exports.getNewsById = (req, res) => {
    const newsId = req.params.id;
    db.query('SELECT * FROM news WHERE id = ?', [newsId], (err, newsRows) => {
        if (err || newsRows.length === 0) return res.status(404).json({error: '找不到新聞'});
        const news = newsRows[0];
        db.query('SELECT title, image FROM news_related WHERE news_id = ?', [newsId], (err2, relatedRows) => {
            news.related = relatedRows || [];
            res.json(news);
        });
    });
};