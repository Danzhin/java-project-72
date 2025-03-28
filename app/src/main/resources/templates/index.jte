@import hexlet.code.utils.NamedRoutes
@param hexlet.code.dto.BasePage page 

@template.layout.page(
    content = @`
        @if(page.getFlash() != null)
            <div>
                <p>${page.getFlash()}</p>
            </div>
        @endif
        <form action="${NamedRoutes.urlsPath()}" method="post">
            <label>
                <input type="text" name="url" required>
            </label>
            <button type="submit">Проверить</button>
        </form>
    `
)