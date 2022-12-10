
describe('The matches page', () => {
  
  beforeEach(() => {
    cy.visit('http://localhost:3000/matches')
  })
  
  it('gets all matches from database', () => {
    cy.intercept('GET', 'http://localhost:8080/matches')
  })
  
  it('goes to checkout when button "Buy tickets" is clicked', () => {
    cy.get('button').contains('Buy tickets').click()
    cy.url().should('include', 'ticketSale')
  })
})