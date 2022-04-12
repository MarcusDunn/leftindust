// smoke.spec.js created with Cypress
//
// Start writing your Cypress tests below!
// If you're unfamiliar with how Cypress works,
// check out the link below and learn how to write your first test:
// https://on.cypress.io/writing-first-test
describe("smoke tests", () => {
    it("opens to a page containing leftindust", () => {
        cy.visit("localhost:3000")
        cy.contains("leftindust")
    })
});