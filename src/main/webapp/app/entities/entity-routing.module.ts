import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'dados-pessoais',
        data: { pageTitle: 'hunterappApp.userDadosPessoais.home.title' },
        loadChildren: () => import('./user/dados-pessoais/dados-pessoais.module').then(m => m.DadosPessoaisModule),
      },
      {
        path: 'endereco',
        data: { pageTitle: 'hunterappApp.userEndereco.home.title' },
        loadChildren: () => import('./user/endereco/endereco.module').then(m => m.EnderecoModule),
      },
      {
        path: 'mensagem',
        data: { pageTitle: 'hunterappApp.userMensagem.home.title' },
        loadChildren: () => import('./user/mensagem/mensagem.module').then(m => m.MensagemModule),
      },
      {
        path: 'aviso',
        data: { pageTitle: 'hunterappApp.userAviso.home.title' },
        loadChildren: () => import('./user/aviso/aviso.module').then(m => m.AvisoModule),
      },
      {
        path: 'documento',
        data: { pageTitle: 'hunterappApp.userDocumento.home.title' },
        loadChildren: () => import('./user/documento/documento.module').then(m => m.DocumentoModule),
      },
      {
        path: 'foto',
        data: { pageTitle: 'hunterappApp.fotoFoto.home.title' },
        loadChildren: () => import('./foto/foto/foto.module').then(m => m.FotoModule),
      },
      {
        path: 'foto-avatar',
        data: { pageTitle: 'hunterappApp.fotoFotoAvatar.home.title' },
        loadChildren: () => import('./foto/foto-avatar/foto-avatar.module').then(m => m.FotoAvatarModule),
      },
      {
        path: 'foto-icon',
        data: { pageTitle: 'hunterappApp.fotoFotoIcon.home.title' },
        loadChildren: () => import('./foto/foto-icon/foto-icon.module').then(m => m.FotoIconModule),
      },
      {
        path: 'foto-documento',
        data: { pageTitle: 'hunterappApp.fotoFotoDocumento.home.title' },
        loadChildren: () => import('./foto/foto-documento/foto-documento.module').then(m => m.FotoDocumentoModule),
      },
      {
        path: 'tipo-mensagem',
        data: { pageTitle: 'hunterappApp.configTipoMensagem.home.title' },
        loadChildren: () => import('./config/tipo-mensagem/tipo-mensagem.module').then(m => m.TipoMensagemModule),
      },
      {
        path: 'raca',
        data: { pageTitle: 'hunterappApp.configRaca.home.title' },
        loadChildren: () => import('./config/raca/raca.module').then(m => m.RacaModule),
      },
      {
        path: 'religiao',
        data: { pageTitle: 'hunterappApp.configReligiao.home.title' },
        loadChildren: () => import('./config/religiao/religiao.module').then(m => m.ReligiaoModule),
      },
      {
        path: 'estado-civil',
        data: { pageTitle: 'hunterappApp.configEstadoCivil.home.title' },
        loadChildren: () => import('./config/estado-civil/estado-civil.module').then(m => m.EstadoCivilModule),
      },
      {
        path: 'tipo-pessoa',
        data: { pageTitle: 'hunterappApp.configTipoPessoa.home.title' },
        loadChildren: () => import('./config/tipo-pessoa/tipo-pessoa.module').then(m => m.TipoPessoaModule),
      },
      {
        path: 'tipo-documento',
        data: { pageTitle: 'hunterappApp.configTipoDocumento.home.title' },
        loadChildren: () => import('./config/tipo-documento/tipo-documento.module').then(m => m.TipoDocumentoModule),
      },
      {
        path: 'dados-pessoais-1',
        data: { pageTitle: 'hunterappApp.userDadosPessoais.home.title' },
        loadChildren: () => import('./user/dados-pessoais/dados-pessoais.module').then(m => m.DadosPessoaisModule),
      },
      {
        path: 'matricula',
        data: { pageTitle: 'hunterappApp.matriculaMatricula.home.title' },
        loadChildren: () => import('./matricula/matricula/matricula.module').then(m => m.MatriculaModule),
      },
      {
        path: 'turma',
        data: { pageTitle: 'hunterappApp.matriculaTurma.home.title' },
        loadChildren: () => import('./matricula/turma/turma.module').then(m => m.TurmaModule),
      },
      {
        path: 'periodo-duracao',
        data: { pageTitle: 'hunterappApp.configPeriodoDuracao.home.title' },
        loadChildren: () => import('./config/periodo-duracao/periodo-duracao.module').then(m => m.PeriodoDuracaoModule),
      },
      {
        path: 'dia-semana',
        data: { pageTitle: 'hunterappApp.configDiaSemana.home.title' },
        loadChildren: () => import('./config/dia-semana/dia-semana.module').then(m => m.DiaSemanaModule),
      },
      {
        path: 'dados-medico',
        data: { pageTitle: 'hunterappApp.matriculaDadosMedico.home.title' },
        loadChildren: () => import('./matricula/dados-medico/dados-medico.module').then(m => m.DadosMedicoModule),
      },
      {
        path: 'vacina',
        data: { pageTitle: 'hunterappApp.matriculaVacina.home.title' },
        loadChildren: () => import('./matricula/vacina/vacina.module').then(m => m.VacinaModule),
      },
      {
        path: 'doenca',
        data: { pageTitle: 'hunterappApp.matriculaDoenca.home.title' },
        loadChildren: () => import('./matricula/doenca/doenca.module').then(m => m.DoencaModule),
      },
      {
        path: 'alergia',
        data: { pageTitle: 'hunterappApp.matriculaAlergia.home.title' },
        loadChildren: () => import('./matricula/alergia/alergia.module').then(m => m.AlergiaModule),
      },
      {
        path: 'avaliacao-economica',
        data: { pageTitle: 'hunterappApp.matriculaAvaliacaoEconomica.home.title' },
        loadChildren: () => import('./matricula/avaliacao-economica/avaliacao-economica.module').then(m => m.AvaliacaoEconomicaModule),
      },
      {
        path: 'acompanhamento-aluno',
        data: { pageTitle: 'hunterappApp.matriculaAcompanhamentoAluno.home.title' },
        loadChildren: () => import('./matricula/acompanhamento-aluno/acompanhamento-aluno.module').then(m => m.AcompanhamentoAlunoModule),
      },
      {
        path: 'item-materia',
        data: { pageTitle: 'hunterappApp.matriculaItemMateria.home.title' },
        loadChildren: () => import('./matricula/item-materia/item-materia.module').then(m => m.ItemMateriaModule),
      },
      {
        path: 'materia',
        data: { pageTitle: 'hunterappApp.matriculaMateria.home.title' },
        loadChildren: () => import('./matricula/materia/materia.module').then(m => m.MateriaModule),
      },
      {
        path: 'caracteristicas-psiquicas',
        data: { pageTitle: 'hunterappApp.matriculaCaracteristicasPsiquicas.home.title' },
        loadChildren: () =>
          import('./matricula/caracteristicas-psiquicas/caracteristicas-psiquicas.module').then(m => m.CaracteristicasPsiquicasModule),
      },
      {
        path: 'colaborador',
        data: { pageTitle: 'hunterappApp.userColaborador.home.title' },
        loadChildren: () => import('./user/colaborador/colaborador.module').then(m => m.ColaboradorModule),
      },
      {
        path: 'tipo-contratacao',
        data: { pageTitle: 'hunterappApp.controleTipoContratacao.home.title' },
        loadChildren: () => import('./controle/tipo-contratacao/tipo-contratacao.module').then(m => m.TipoContratacaoModule),
      },
      {
        path: 'agenda-colaborador',
        data: { pageTitle: 'hunterappApp.userAgendaColaborador.home.title' },
        loadChildren: () => import('./user/agenda-colaborador/agenda-colaborador.module').then(m => m.AgendaColaboradorModule),
      },
      {
        path: 'exame-medico',
        data: { pageTitle: 'hunterappApp.eventoExameMedico.home.title' },
        loadChildren: () => import('./evento/exame-medico/exame-medico.module').then(m => m.ExameMedicoModule),
      },
      {
        path: 'foto-exame-medico',
        data: { pageTitle: 'hunterappApp.fotoFotoExameMedico.home.title' },
        loadChildren: () => import('./foto/foto-exame-medico/foto-exame-medico.module').then(m => m.FotoExameMedicoModule),
      },
      {
        path: 'evento',
        data: { pageTitle: 'hunterappApp.eventoEvento.home.title' },
        loadChildren: () => import('./evento/evento/evento.module').then(m => m.EventoModule),
      },
      {
        path: 'endereco-evento',
        data: { pageTitle: 'hunterappApp.eventoEnderecoEvento.home.title' },
        loadChildren: () => import('./evento/endereco-evento/endereco-evento.module').then(m => m.EnderecoEventoModule),
      },
      {
        path: 'pagar',
        data: { pageTitle: 'hunterappApp.controlePagar.home.title' },
        loadChildren: () => import('./controle/pagar/pagar.module').then(m => m.PagarModule),
      },
      {
        path: 'tipo-pagar',
        data: { pageTitle: 'hunterappApp.controleTipoPagar.home.title' },
        loadChildren: () => import('./controle/tipo-pagar/tipo-pagar.module').then(m => m.TipoPagarModule),
      },
      {
        path: 'pagar-para',
        data: { pageTitle: 'hunterappApp.controlePagarPara.home.title' },
        loadChildren: () => import('./controle/pagar-para/pagar-para.module').then(m => m.PagarParaModule),
      },
      {
        path: 'foto-pagar',
        data: { pageTitle: 'hunterappApp.fotoFotoPagar.home.title' },
        loadChildren: () => import('./foto/foto-pagar/foto-pagar.module').then(m => m.FotoPagarModule),
      },
      {
        path: 'receber',
        data: { pageTitle: 'hunterappApp.controleReceber.home.title' },
        loadChildren: () => import('./controle/receber/receber.module').then(m => m.ReceberModule),
      },
      {
        path: 'tipo-receber',
        data: { pageTitle: 'hunterappApp.controleTipoReceber.home.title' },
        loadChildren: () => import('./controle/tipo-receber/tipo-receber.module').then(m => m.TipoReceberModule),
      },
      {
        path: 'receber-de',
        data: { pageTitle: 'hunterappApp.controleReceberDe.home.title' },
        loadChildren: () => import('./controle/receber-de/receber-de.module').then(m => m.ReceberDeModule),
      },
      {
        path: 'foto-receber',
        data: { pageTitle: 'hunterappApp.fotoFotoReceber.home.title' },
        loadChildren: () => import('./foto/foto-receber/foto-receber.module').then(m => m.FotoReceberModule),
      },
      {
        path: 'tipo-transacao',
        data: { pageTitle: 'hunterappApp.controleTipoTransacao.home.title' },
        loadChildren: () => import('./controle/tipo-transacao/tipo-transacao.module').then(m => m.TipoTransacaoModule),
      },
      {
        path: 'estoque',
        data: { pageTitle: 'hunterappApp.controleEstoque.home.title' },
        loadChildren: () => import('./controle/estoque/estoque.module').then(m => m.EstoqueModule),
      },
      {
        path: 'produto',
        data: { pageTitle: 'hunterappApp.controleProduto.home.title' },
        loadChildren: () => import('./controle/produto/produto.module').then(m => m.ProdutoModule),
      },
      {
        path: 'foto-produto',
        data: { pageTitle: 'hunterappApp.fotoFotoProduto.home.title' },
        loadChildren: () => import('./foto/foto-produto/foto-produto.module').then(m => m.FotoProdutoModule),
      },
      {
        path: 'entrada-estoque',
        data: { pageTitle: 'hunterappApp.controleEntradaEstoque.home.title' },
        loadChildren: () => import('./controle/entrada-estoque/entrada-estoque.module').then(m => m.EntradaEstoqueModule),
      },
      {
        path: 'foto-entrada-estoque',
        data: { pageTitle: 'hunterappApp.fotoFotoEntradaEstoque.home.title' },
        loadChildren: () => import('./foto/foto-entrada-estoque/foto-entrada-estoque.module').then(m => m.FotoEntradaEstoqueModule),
      },
      {
        path: 'saida-estoque',
        data: { pageTitle: 'hunterappApp.controleSaidaEstoque.home.title' },
        loadChildren: () => import('./controle/saida-estoque/saida-estoque.module').then(m => m.SaidaEstoqueModule),
      },
      {
        path: 'foto-saida-estoque',
        data: { pageTitle: 'hunterappApp.fotoFotoSaidaEstoque.home.title' },
        loadChildren: () => import('./foto/foto-saida-estoque/foto-saida-estoque.module').then(m => m.FotoSaidaEstoqueModule),
      },
      {
        path: 'grupo-produto',
        data: { pageTitle: 'hunterappApp.controleGrupoProduto.home.title' },
        loadChildren: () => import('./controle/grupo-produto/grupo-produto.module').then(m => m.GrupoProdutoModule),
      },
      {
        path: 'sub-grupo-produto',
        data: { pageTitle: 'hunterappApp.controleSubGrupoProduto.home.title' },
        loadChildren: () => import('./controle/sub-grupo-produto/sub-grupo-produto.module').then(m => m.SubGrupoProdutoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
