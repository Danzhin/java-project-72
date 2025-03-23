@param hexlet.code.dto.BasePage page 

@template.layout.page(
    content = @`
        @if(page.getFlash() != null)
            <p>${page.getFlash()}</p>
        @endif
        <form action="/urls" method="post">
            <input type="text" name="url" required>
            <button type="submit">Проверить</button>
        </form>
    `
)