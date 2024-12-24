# גְלֶבָּה-דרויד
מחלקות עזר לבניית משחקים באדנרויד באמצעות ג'אווה.  
(פורט של [גלבה לפייתון](https://github.com/rotlug/gleba))

### איך משתמשים?
כדי להפוך Activity למשחק:
```java
public class ExampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView gameView = new GameView(this, 60, Color.BLACK);
        setContentView(gameView);
    }
}
```

...עכשיו המשחק מוצג בActivity עם 60 FPS ורקע שחור (ניתן לשנות את הערכים האלה בבניית `GameView`)

### חוליות
כל עצם במשחק מוצג בתור חוליה (`Node`). הנה דוגמה למלבן אדום שזז למטה כל פריים בפיקסל אחד:
```java
class DownRect extends ColorRect {
    public DownRect() {
        super(Color.RED);
    }

    @Override
    public void onReady() {
        super.onReady();
        // קבע את הגודל של המלבן (100x100)
        setSize(100, 100);
    }

    // הפעולה update נקראת כל פריים
    @Override
    public void update(Canvas canvas, MotionEvent motionEvent) {
        super.update(canvas, motionEvent);
        // זז למטה
        position.y += 1;
    }
}
```

כדי להוסיף את העצם הזה למשחק:
```
gameView.getRootNode().addChild(new DownRect());
```