@template.layout.page(
    content = @`
        <form action="/urls" method="post">
            <input type="text" name="url">
            <button type="submit">Проверить</button>
        </form>
    `
)