# כיבוי והדלקה של השלמה אוטומטית

חשוב לדעת מראש: אין בעורך שום פקודה שמכבה את ההשלמה האוטומטית.
הדרך היחידה היא קובץ ההגדרות.

---

## עזרה ידנית כשאתה נתקע

אלה עובדים גם כשהכל כבוי. זה הרעיון של מצב התרגול.

| מה זה עושה | הקיצור |
|---|---|
| חלון השלמה, פעם אחת | `Alt + Space` |
| חתימת המתודה והפרמטרים | `Alt + Shift + Space` |

על מקלדת של מק המקש הזה נקרא Option. על מקלדת רגילה זה מקש Alt. אותו מקש.

הקיצורים מוגדרים בקובץ:

```
~/Library/Application Support/Code/User/keybindings.json
```

---

## שלושת סוגי העזרה

לכל סוג יש הגדרה נפרדת. אין מתג אחד שמכבה את כולם.

| סוג העזרה | איך זה נראה | ההגדרה |
|---|---|---|
| רשימת השלמות | חלון לבן שקופץ בהקלדה | `editor.quickSuggestions` |
| הצעת בינה מלאכותית | טקסט אפור בתוך השורה | `editor.inlineSuggest.enabled` |
| רמזי פרמטרים | טקסט אפור קטן בתוך קריאה | `java.inlayHints.parameterNames.enabled` |

---

## הדלקה מחדש של הכל

הקובץ:

```
.vscode/settings.json
```

בתוך המקטע של ג'אווה, שנה לערכים האלה:

```json
"editor.quickSuggestions": { "other": "on", "comments": "off", "strings": "off" },
"editor.suggestOnTriggerCharacters": true,
"editor.inlineSuggest.enabled": true,
"editor.parameterHints.enabled": true,
"editor.acceptSuggestionOnEnter": "on",
"editor.tabCompletion": "on",
"editor.snippetSuggestions": "inline",
"editor.wordBasedSuggestions": "matchingDocuments"
```

ומחוץ למקטע:

```json
"java.inlayHints.parameterNames.enabled": "all",
"github.copilot.enable": { "*": true, "java": true }
```

כדי לכבות בחזרה, החזר את הערכים ההפוכים. הקובץ מתועד בהערות.

---

## השתקה זמנית של הבינה המלאכותית בלבד

זה הדבר היחיד שיש לו פקודה אמיתית. פתח את לוח הפקודות:

```
Cmd + Shift + P
```

והרץ:

```
Snooze Inline Suggestions
```

זה משתיק רק את הטקסט האפור, ורק לזמן קצר. זה לא נוגע בחלון ההשלמות.

---

## אחרי כל שינוי הגדרות

```
Cmd + Shift + P
```

ואז:

```
Developer: Reload Window
```
