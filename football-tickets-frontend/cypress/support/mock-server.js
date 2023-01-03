function configureMockTeamsServer() {
    cy.server();
    cy.route({
        method: 'POST',
        url: '/teams/add'
    });
}

module.exports = configureMockTeamsServer;