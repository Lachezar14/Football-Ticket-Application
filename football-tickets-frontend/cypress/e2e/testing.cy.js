describe('The login page', () => {

  beforeEach(() => {
    cy.visit('http://localhost:3000/login')
  })

  it('logs in with admin credentials', () => {
    cy.get('input[name=username]').type('lucho@gmail.com')
    cy.get('input[name=password]').type('12345')
    cy.get('button[type=submit]').click()
    cy.url().should('include', 'admin')
  })
})

describe('The teams page', () => {
  
  beforeEach(() => {
    cy.visit('http://localhost:3000/admin/teams')
  })

  it('adds new team to database', () => {
 
    cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(1) > div.MuiPaper-root.MuiPaper-elevation.MuiPaper-rounded.MuiPaper-elevation1.MuiCard-root.css-19d2s13-MuiPaper-root-MuiCard-root > div > form > div:nth-child(1) > div').type('TestTeam FC')
    cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(1) > div.MuiPaper-root.MuiPaper-elevation.MuiPaper-rounded.MuiPaper-elevation1.MuiCard-root.css-19d2s13-MuiPaper-root-MuiCard-root > div > form > div:nth-child(2)').type('Blabal')
    cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(1) > div.MuiPaper-root.MuiPaper-elevation.MuiPaper-rounded.MuiPaper-elevation1.MuiCard-root.css-19d2s13-MuiPaper-root-MuiCard-root > div > form > div:nth-child(3)').type('5000')
    cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(1) > div.MuiPaper-root.MuiPaper-elevation.MuiPaper-rounded.MuiPaper-elevation1.MuiCard-root.css-19d2s13-MuiPaper-root-MuiCard-root > div > form > button').click()
  
    cy.contains('Team created successfully')
  });

  it('shows error message when trying to add a team that exists', () => {
    cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(1) > div.MuiPaper-root.MuiPaper-elevation.MuiPaper-rounded.MuiPaper-elevation1.MuiCard-root.css-19d2s13-MuiPaper-root-MuiCard-root > div > form > div:nth-child(1) > div').type('TestTeam FC')
    cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(1) > div.MuiPaper-root.MuiPaper-elevation.MuiPaper-rounded.MuiPaper-elevation1.MuiCard-root.css-19d2s13-MuiPaper-root-MuiCard-root > div > form > div:nth-child(2)').type('Blabal')
    cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(1) > div.MuiPaper-root.MuiPaper-elevation.MuiPaper-rounded.MuiPaper-elevation1.MuiCard-root.css-19d2s13-MuiPaper-root-MuiCard-root > div > form > div:nth-child(3)').type('5000')
    cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(1) > div.MuiPaper-root.MuiPaper-elevation.MuiPaper-rounded.MuiPaper-elevation1.MuiCard-root.css-19d2s13-MuiPaper-root-MuiCard-root > div > form > button').click()

    cy.contains('Team already exists')
  });
  
    it('updates team from database', () => {
      
      cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(2) > div > div > form > div.MuiFormControl-root' +
          '.MuiFormControl-marginNormal.MuiFormControl-fullWidth.MuiTextField-root.css-1v94dqm-MuiFormControl-root-MuiTextField-root').click()
      cy.get('li').contains('TestTeam FC').click()

      cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(2) > div > div > form > div:nth-child(2) > div').clear().type('NewTeam FC')
      cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(2) > div > div > form > div:nth-child(3)').clear().type('NewBlabal')
      cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(2) > div > div > form > div:nth-child(4)').clear().type('10000')
      cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(2) > div > div > form > button').click()
      cy.contains('Team updated successfully')
    })
  
    it('deletes team from database', () => {
      cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(2) > div > div > form > div.MuiFormControl-root' +
          '.MuiFormControl-marginNormal.MuiFormControl-fullWidth.MuiTextField-root.css-1v94dqm-MuiFormControl-root-MuiTextField-root').click()
      cy.get('li').contains('NewTeam FC').click()
      cy.get('#root > div > div > div.MuiGrid-root.MuiGrid-container.css-10mzr36-MuiGrid-root > div:nth-child(1) > div' +
          '.MuiGrid-root.MuiGrid-item.css-vkfc0g-MuiGrid-root > div > div > form > button').click()
      cy.contains('Team deleted successfully')
    })
})